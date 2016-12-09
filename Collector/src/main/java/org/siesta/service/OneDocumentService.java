package org.siesta.service;

import org.siesta.error.DocumentNotFindException;
import org.siesta.error.NotContentException;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * This class
 */
@Service
public class OneDocumentService implements RepositoryService {

    private final Logger logger = LoggerFactory.getLogger(OneDocumentService.class);

    @Autowired
    private OneRepoConnector connector;
    @Autowired
    private CachedDocumentService cachedService;
    private List<RepoConnector> connectors = new ArrayList<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

/*    @PostConstruct
    public void initConnectors() {// TODO add ability registration connectors
        OneRepoConnector repoOneConnector = new OneRepoConnector("admin", "admin");
        OneRepoConnector repoOneConnector2 = new OneRepoConnector("admin", "admin");
        repoOneConnector.setRepoName("repoOne");
        repoOneConnector.setUrl("http://localhost:8080/rest");
        repoOneConnector2.setUrl("http://localhost:8082/rest");
        repoOneConnector2.setRepoName("repoTwo");
        connectors.add(repoOneConnector);
    }*/

    @Override
    public void addConnector(RepoConnector repoConnector) {
        connectors.add(repoConnector);
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
        RepoConnector repoConnector = connectors.get(0);
        repoConnector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/", HttpMethod.POST, documentRepoOne, new ParameterizedTypeReference<DocumentRepoOne>() {
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
        RepoConnector repoConnector = connectors.get(0);
        DocumentRepoOne doc = repoConnector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/" + savedDoc.getDocId(), HttpMethod.PUT, documentRepoOne, new ParameterizedTypeReference<DocumentRepoOne>() {
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
        Boolean res = connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/" + docId, HttpMethod.DELETE, new ParameterizedTypeReference<Boolean>() {
        });
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
                        connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/" + name + "/", HttpMethod.GET, new ParameterizedTypeReference<DocumentRepoOne>() {
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
        for (RepoConnector repoConnector : connectors) {
            Callable<T> future = () -> repoConnector.connect(repoConnector.getUrl() + url, method, parametrezedType);
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
