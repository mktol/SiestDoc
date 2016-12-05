package org.siesta.service;

import org.siesta.model.Document;
import org.siesta.model.DocumentRepoOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @Autowired
    private OneRepoConnector connector;
    @Autowired
    private CachedDocumentService documentService;
    private List<RepoConnector> connectors = new ArrayList<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    @PostConstruct
    public void initConnectors() {// TODO add ability registration connectors
        OneRepoConnector repoOneConnector = new OneRepoConnector("admin", "admin", "8080");
        OneRepoConnector repoOneConnector2 = new OneRepoConnector("admin", "admin", "8082");
        repoOneConnector.setRepoName("repoOne");
        repoOneConnector2.setRepoName("repoTwo");
        connectors.add(repoOneConnector);
        connectors.add(repoOneConnector2);
    }

    public void addConnector(RepoConnector repoConnector) {
        connectors.add(repoConnector);
    }

    @Override
    public List<Document> getAllDocuments() throws InterruptedException {

        List<Callable<List<DocumentRepoOne>>> tasks = new ArrayList<>();
        for (RepoConnector repoConnector : connectors) {
            Callable<List<DocumentRepoOne>> future = () -> repoConnector.connect(((OneRepoConnector) repoConnector).restServiceUrl + "/documents/", HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>() {
            } );
            tasks.add(future);
        }
        List<List<DocumentRepoOne>> repoOnes = executor.invokeAll(tasks).stream().map(listFuture -> {
            try {
                return listFuture.get();
            } catch (Exception ex) {

                return new ArrayList<DocumentRepoOne>();
            }
        }).collect(Collectors.toList()); // make own collector

        List<DocumentRepoOne> res = new ArrayList<>();
        for (List<DocumentRepoOne> repoOne : repoOnes) {
            res.addAll(repoOne);
        }
        return ConverterUtil.convertToDocumetnList(res);

/*        List<DocumentRepoOne> documentRepoOnes = connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/", HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>(){});
        List<Document> resultList = ConverterUtil.convertToDocumetnList(documentRepoOnes);
        documentService.saveDocument(resultList);
        return resultList;*/
    }

    @Override
    public Optional<Document> getDocument(Long id) {
        Optional<Document> doc = Optional.of(documentService.getDoc(id));
        return doc;
    }

    @Override
    public boolean addDocument(Document document) {
        documentService.saveUpdateDoc(document);
        RepoConnector repoConnector = connectors.get(0);
/*        repoConnector.connect()*/
        // Repo manager save doc in one of repository
        return false;
    }

    @Override
    public boolean update(Document document) {
        return false;
    }

    @Override
    public boolean delete(String docId) {
        Boolean res = connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/" + docId, HttpMethod.DELETE, new ParameterizedTypeReference<Boolean>() {

        });

        // boolean resp = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+docId2, HttpMethod.DELETE, request, new ParameterizedTypeReference<Boolean>(){}).getBody();

        return res;
    }

    @Override
    public Document getDocumentByName(String name) {
        return connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/" + name + "/", HttpMethod.GET, new ParameterizedTypeReference<Document>() {

        });
    }

    @Override
    public List<Document> getDocByDocId(String docId) {
        List<DocumentRepoOne> res = connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents?docId=" + docId, HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>() {

        });
        return ConverterUtil.convertToDocumetnList(res);
    }
}
