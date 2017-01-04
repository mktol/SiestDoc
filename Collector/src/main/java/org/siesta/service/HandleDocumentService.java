package org.siesta.service;

import org.siesta.aspect.RemoveFromCache;
import org.siesta.error.DocumentNotFindException;
import org.siesta.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Handle document service
 */
@Service
public class HandleDocumentService {

    private final Logger logger = LoggerFactory.getLogger(HandleDocumentService.class);
    ExecutorService executor = Executors.newCachedThreadPool();
    @Autowired
    private CachedDocumentService cachedDocumentService;
    private Map<String, SiestaConnector> connectorMap = new HashMap<>();


    public Map<String, SiestaConnector> getConnectors() {
        return connectorMap;
    }

    public boolean addConnector(SiestaConnector connector) {
        connectorMap.put(connector.getName(), connector);
        return connectorMap.containsKey(connector.getName());
    }

    public List<Document> getAll() {

        List<FutureTask<List<Document>>> tasks = getFutureTasks();

        List<Document> resultList = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();
        for (FutureTask<List<Document>> task : tasks) {
            try {
                List<Document> currentDocs = task.get();
                resultList.addAll(currentDocs);
            } catch (InterruptedException e) {
                logger.error("Problem with execution task ", e);
                exceptions.add(e);
            } catch (ExecutionException e) {
                logger.error("Problem with connection or returned result from repository", e);
                exceptions.add(e);
            }
        }
        handleExceptions(exceptions);
        return resultList;
    }

    private void handleExceptions(List<Exception> exceptionList) {
        for (Exception exception : exceptionList) {
            if(exception.getCause() instanceof HttpMessageNotReadableException){
                logger.error("problem with mapping returned result to object, "+exception.getCause().getMessage());
            }
        }
    }


    private List<FutureTask<List<Document>>> getFutureTasks() {

        List<FutureTask<List<Document>>> tasks = new ArrayList<>();
        try {
            for (SiestaConnector connector : connectorMap.values()) {
                FutureTask<List<Document>> task = new FutureTask<>(connector::getAll);
                tasks.add(task);
                executor.submit(task);
            }

        }catch (Exception excep){
            logger.error("");
        }
        return tasks;
    }


    public Document getDocumentById(String docId) { // Check in cash if exist Document with this id and return it;

        String repoName = DocumentUtil.getNameFromId(docId);
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        String documentId = DocumentUtil.getDocumentId(docId);
        if (documentId.isEmpty()) {
            throw new DocumentNotFindException();
        }
        if (siestaConnector != null) {
            return siestaConnector.getDocumentById(documentId);
        }
        return getDocumentByAllRepo(documentId); // Cache it before returning
    }

    private Document getDocumentByAllRepo(final String documentId) {
        List<FutureTask<Document>> tasks = new ArrayList<>();
        for (SiestaConnector connector : connectorMap.values()) {
            FutureTask<Document> task = new FutureTask<>(() -> connector.getDocumentById(documentId));
            tasks.add(task);
            executor.submit(task);
        }
        List<Document> resultList = new ArrayList<>();
        for (FutureTask<Document> task : tasks) {
            try {
                Document document = task.get();
                resultList.add(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() > 1) {
            logger.error("Find equals document id, result list size " + resultList.size());
        }
        return resultList.get(0);

    }

    private SiestaConnector getConnectorByName(String repoName) {
        return connectorMap.get(repoName);
    }

    @RemoveFromCache
    public boolean updateDocument(Document document) {
        String docId = document.getDocId();
        String repoName = DocumentUtil.getNameFromId(docId);
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        if (siestaConnector == null) { // move this code to get connector by name
            throw new DocumentNotFindException(docId);
        }
        return siestaConnector.updateDocument(document);
    }

    @RemoveFromCache
    public boolean deleteDocument(String docId) { // remove from cache if exist
//        cachedDocumentService.deleteDocument(docId);
        String repoName = DocumentUtil.getNameFromId(docId);
        String cleanDocId = DocumentUtil.getDocumentId(docId);
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        if (siestaConnector == null) {
            throw new DocumentNotFindException(docId);
        }
        return siestaConnector.deleteDocument(cleanDocId);
    }

    public Document addDocument(Document document) {
        if (document.getDocId() == null || document.getDocId().length() == 36) {
            if(connectorMap.size()>0) {
                SiestaConnector siestaConnector = (SiestaConnector) connectorMap.values().toArray()[0];
                return siestaConnector.addDocument(document);
            }
        }
        String repoName = DocumentUtil.getNameFromId(document.getDocId());
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        if (siestaConnector == null) {
            throw new DocumentNotFindException(document.getDocId());
        }
        return siestaConnector.addDocument(document); // put in cash
    }

    /**
     * Method check alive connection.
     */
    @Scheduled(fixedRate = 30_000)
    public void connectorsChecker() {
        List<SiestaConnector> unWorkedConnectors = new ArrayList<>();
        for (SiestaConnector connector : connectorMap.values()) {
            if (!connector.isConnectionALive()) {
                unWorkedConnectors.add(connector);
                logger.warn(" Connector " + connector.getName() + " is not accessible. Connector is removed. ");
            }
        }
        if (!unWorkedConnectors.isEmpty()) {
            for (SiestaConnector connector : unWorkedConnectors) {
                connectorMap.remove(connector.getName());
            }
        }
    }
}
