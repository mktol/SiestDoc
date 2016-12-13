package org.siesta.service;

import org.siesta.error.DocumentNotFindException;
import org.siesta.error.NotContentException;
import org.siesta.error.RepoConnectionException;
import org.siesta.model.Document;
import org.siesta.model.DocumentRepoOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * This class
 */
@Service
public class ManageDocumentService implements RepositoryService {

    private final Logger logger = LoggerFactory.getLogger(ManageDocumentService.class);

    @Autowired
    private RepoConnector connector;
    @Autowired
    private CachedDocumentService cachedService;
    private List<Connector> connectors = new ArrayList<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public boolean addConnector(Connector connector) {
        return connectors.add(connector);
    }

    public boolean removeConnector(String name){
        Connector removed = connectors.stream().filter(conn->conn.getPepoName().equals(name)).findFirst().orElse(null);
        return removed == null || connectors.remove(removed);
    }

    /**
     * Get all documents in all repositories
     * @return all documents
     */
    @Override
    public List<Document> getAllDocuments() {

        List<DocumentRepoOne> res = new ArrayList<>();
        List<Callable<List<DocumentRepoOne>>> tasks2 = createTasks("/documents/", HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>() {
        });
        try {
            List<List<DocumentRepoOne>> repoOnes = executor.invokeAll(tasks2).stream()  // TODO make it by for each
                    .map(new ConnectionFunction())
                    .collect(Collectors.toList());
            for (List<DocumentRepoOne> repoOne : repoOnes) {
                res.addAll(repoOne);
            }
            if (res.isEmpty()) {
                throw new DocumentNotFindException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ConverterUtil.convertToDocumetnList(res);
    }

    public List<Document> getAllDocuments2(){
        List<Callable<List<DocumentRepoOne>>> tasks = new ArrayList<>(); // TODO refactor it
        for (Connector connector : connectors) {
            Callable<List<DocumentRepoOne>> future = () -> connector.connect(connector.getUrl() +"/documents/" ,HttpMethod.GET , new ParameterizedTypeReference<List<DocumentRepoOne>>() {});
            tasks.add(future);
        }
        List<DocumentRepoOne> result = new ArrayList<>();
        for (Callable<List<DocumentRepoOne>> task : tasks) {
            Future<List<DocumentRepoOne>> res = executor.submit(task);
            try {
                List<DocumentRepoOne> docs= res.get(20, TimeUnit.SECONDS);
                result.addAll(docs);
            } catch (Exception e) {
                logger.error("problem with connection to repository. "+e.getMessage());
                removeConnector(e);
            }
        }
        List<Document> documents = ConverterUtil.convertToDocumetnList(result);
        return ConverterUtil.convertToDocumetnList(result);
    }

    /**
     * Remove connector from cached connectors if application cant connect ot repository.
     * @param connectionError
     */
    private void removeConnector(Exception connectionError){
        if(connectionError.getCause() instanceof RepoConnectionException){
            removeConnector(connectionError.getCause().getMessage());
        }
    }

    @Override
    public Document getDocument(Long id) {

        Document doc = cachedService.getDoc(id);
        logger.debug("get document by id" + id);
        return doc;
    }

    @Override
    public Document addDocument(Document document) {
        Document savedDoc = cachedService.saveUpdateDoc(document);
        DocumentRepoOne documentRepoOne = ConverterUtil.convertToRepoOneDocument(savedDoc);
        Connector connector = connectors.get(0);
        connector.connect(connector.getUrl() + "/documents/", HttpMethod.POST, documentRepoOne, new ParameterizedTypeReference<DocumentRepoOne>() {
        });
        logger.debug("add document. "+savedDoc);
        // Repo manager save doc in one of repository
        return savedDoc;
    }

    @Override
    public boolean update(Document document) {
        validateUpdateDoc(document);
        Document savedDoc = cachedService.saveUpdateDoc(document);
        DocumentRepoOne documentRepoOne = ConverterUtil.convertToRepoOneDocument(savedDoc);
        Connector connector = connectors.get(0);
        DocumentRepoOne doc = connector.connect(connector.getUrl() + "/documents/" + savedDoc.getDocId(), HttpMethod.PUT, documentRepoOne, new ParameterizedTypeReference<DocumentRepoOne>() {
        });
        if (doc == null) {
            logger.debug("cant update document with id "+doc.getId());
            return false;
        }
        logger.debug("update document with id "+doc.getId());
        return true;
    }


    @Override
    public boolean delete(String docId) {
        cachedService.deleteDocument(docId); //TODO find Repository by repo name
        Boolean res = connector.connect(connector.getUrl() + "/documents/" + docId, HttpMethod.DELETE, new ParameterizedTypeReference<Boolean>() {});
        return res;
    }

    /**
     * get document by name in repository
     * @param name mame of repository
     * @return document
     */
    @Override
    public Document getDocumentByName(String name) {
        Optional<Document> document = cachedService.getDocumentByName(name);
        return document.orElse(
                ConverterUtil.convertToDocument(
                        connector.connect(connector.getUrl() + "/documents/" + name + "/", HttpMethod.GET, new ParameterizedTypeReference<DocumentRepoOne>() {
        })));

    }

    /**
     * Find document by document id in all repositories
     * @param docId
     * @return list of documents
     */
    @Override
    public List<Document> getDocByDocId(String docId) {

        List<Callable<List<DocumentRepoOne>>> tasks = createTasks("/documents/" + docId, HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>() {
        });
        List<List<DocumentRepoOne>> repoOnes = null; // make own collector
        List<DocumentRepoOne> res = new ArrayList<>();
        try {
            repoOnes = executor.invokeAll(tasks).stream()
                    .map(new ConnectionFunction())
                    .collect(Collectors.toList());
            for (List<DocumentRepoOne> repoOne : repoOnes) {
                res.addAll(repoOne);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (res.isEmpty()) {
            throw new DocumentNotFindException();
        }
        return ConverterUtil.convertToDocumetnList(res);
    }

    /**
     * create tasks for different repositories connections
     * @param url connection url
     * @param method Http method
     * @param parametrezedType parametrized type
     * @param <T>
     * @return List of callable tasks
     */
    private <T> List<Callable<T>> createTasks(String url, HttpMethod method, ParameterizedTypeReference<T> parametrezedType) {
        List<Callable<T>> tasks = new ArrayList<>(); // TODO refactor it
        for (Connector connector : connectors) {
            Callable<T> future = () -> connector.connect(connector.getUrl() + url, method, parametrezedType);
            tasks.add(future);
        }
        return tasks;
    }

    private void validateUpdateDoc(Document document) {
        if (document == null) {
            logger.debug("incorrect doc "+document);
            throw new NotContentException("Document is empty");
        }
        validateId(document.getDocId());
    }

    private void validateId(String docId) {
        if (docId == null) {
            throw new DocumentNotFindException();
        }
    }
}
