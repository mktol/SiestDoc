package org.siesta.service;

import org.siesta.model.Document;
import org.siesta.model.DocumentRepoOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class
 */
@Service
public class OneDocumentService implements RepositoryService {

    @Autowired
    private OneRepoConnector connector ;

    @Autowired
    private CachedDocumentService documentService;

    @Override
    public List<Document> getAllDocuments() {
        List<DocumentRepoOne> documentRepoOnes = connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/", HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>(){});
        List<Document> resultList = ConverterUtil.convertToDocumetnList(documentRepoOnes);
        documentService.saveDocument(resultList);
        return resultList;
    }

    @Override
    public Optional<Document> getDocument(Long id) {
        Optional<Document> doc = Optional.of(documentService.getDoc(id));
        return doc;
    }

    @Override
    public boolean addDocument(Document document) {
        documentService.seveUpdateDoc(document);
        // Repo manager save doc in one of repository
        return false;
    }

    @Override
    public boolean update(Document document) {
        return false;
    }

    @Override
    public Document delete(Document document) {
        return null;
    }

    @Override
    public Document getDocumentByName(String name) {
        return connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/"+name+"/", HttpMethod.GET, new ParameterizedTypeReference<Document>() {

        });
    }
}
