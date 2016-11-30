package org.siesta.service;

import org.siesta.model.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class
 */
@Service
public class OneDocumentService implements RepositoryService {
    @Override
    public List<Document> getAllDocuments() {

        return null;
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
