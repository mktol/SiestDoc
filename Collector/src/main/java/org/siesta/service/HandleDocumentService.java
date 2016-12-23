package org.siesta.service;

import org.siesta.error.DocumentNotFindException;
import org.siesta.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private List<SiestaConnector> connectors = new ArrayList<>();

    public List<SiestaConnector> getConnectors() {
        return connectors;
    }

    public boolean addConnector(SiestaConnector connector) {
        return connectors.add(connector);
    }

    public List<Document> getAll() {

        List<Document> documents = cachedDocumentService.getAll();
        if (!documents.isEmpty()) {
            return documents;
        }
        List<FutureTask<List<Document>>> tasks = new ArrayList<>();
        for (SiestaConnector connector : connectors) {
            FutureTask<List<Document>> task = new FutureTask<>(connector::getAll);
            tasks.add(task);
            executor.submit(task);
        }
        List<Document> resultList = new ArrayList<>();
        for (FutureTask<List<Document>> task : tasks) {
            try {
                List<Document> currentDocs = task.get();
                resultList.addAll(currentDocs);
            } catch (Exception e) {//
                logger.error(e.getCause().getMessage());
            }
        }
        cachedDocumentService.saveDocument(resultList);
        return cachedDocumentService.getAll();
    }


    public Document getDocumentById(String docId) {
        Document document = cachedDocumentService.getByDocumentId(docId);
        if (document != null) {
            return document;
        }
        String repoName = ConverterUtil.getNameFromId(docId);
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        String documentId = ConverterUtil.getDocumentId(docId);
        if (documentId.isEmpty()) {
            throw new DocumentNotFindException();
        }
        if (siestaConnector != null) {
            return siestaConnector.getDocumentById(documentId);
        }
        return getDocumentByAllRepo(documentId);
    }

    private Document getDocumentByAllRepo(final String documentId) {
        List<FutureTask<Document>> tasks = new ArrayList<>();
        for (SiestaConnector connector : connectors) {
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
        return connectors.stream().filter(siestaConnector -> siestaConnector.getName().equals(repoName)).findFirst().orElse(null); //connectors save into Map
    }

    public boolean updateDocument(Document document) {
        cachedDocumentService.deleteDocument(document.getDocId());
        String docId = document.getDocId();
        String repoName = ConverterUtil.getNameFromId(docId);
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        if (siestaConnector == null) { // move this code to get connector by name
            throw new DocumentNotFindException(docId);
        }
        return siestaConnector.updateDocument(document);
    }

    public boolean deleteDocument(String docId) {
        cachedDocumentService.deleteDocument(docId);
        String repoName = ConverterUtil.getNameFromId(docId);
        String cleanDocId = ConverterUtil.getDocumentId(docId);
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        if (siestaConnector == null) {
            throw new DocumentNotFindException(docId);
        }
        return siestaConnector.deleteDocument(cleanDocId);
    }

    public Document addDocument(Document document) {
        if (document.getDocId() == null || document.getDocId().length() == 36) {
            SiestaConnector siestaConnector = connectors.get(0);
            Document res = siestaConnector.addDocument(document);
            return res;
        }
        String repoName = ConverterUtil.getNameFromId(document.getDocId());
        SiestaConnector siestaConnector = getConnectorByName(repoName);
        if (siestaConnector == null) {
            throw new DocumentNotFindException(document.getDocId());
        }
        return siestaConnector.addDocument(document);
    }

    /**
     * Method check alive connection.
     */
    @Scheduled(fixedRate = 30_000)
    public void connectorsChecker() {
        List<SiestaConnector> unWorkedConnectors = new ArrayList<>();
        for (SiestaConnector connector : connectors) {
            if (!connector.isConnectionALive()) {
                unWorkedConnectors.add(connector);
                logger.warn(" Connector " + connector.getName() + " is not accessible. Connector is removed. ");
            }
        }
        if (!unWorkedConnectors.isEmpty()) {
            connectors.removeAll(unWorkedConnectors);
        }

    }
}
