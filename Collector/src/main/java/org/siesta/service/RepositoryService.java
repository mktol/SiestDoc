package org.siesta.service;

import org.siesta.model.Document;

import java.util.List;
import java.util.Optional;

/**
 * This class
 */
public interface RepositoryService {

    List<Document> getAllDocuments();
    Optional<Document> getDocument(Long id);
    boolean addDocument(Document document);
    boolean update(Document document);

    boolean delete(String docId);

    Document getDocumentByName(String name);
    List<Document> getDocByDocId(String docId);
}
