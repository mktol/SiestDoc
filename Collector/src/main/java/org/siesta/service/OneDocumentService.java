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

    @Override
    public List<Document> getAllDocuments() {
        List<DocumentRepoOne> documentRepoOnes = connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/", HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>(){});
        return ConverterUtil.convertToDocumetnList(documentRepoOnes);
    }

    @Override
    public Optional<Document> getDocument(Long id) {
        return null;
    }

    @Override
    public boolean addDocument(Document document) {
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
}
