package org.siesta.service;

import org.siesta.model.Document;

import java.util.List;

/**
 * This class
 */
public interface RepositoryService {

    List<Document> getAllDocuments() throws InterruptedException;
    List<Document> getAllDocuments2() throws InterruptedException;
    Document getDocument(Long id);
    Document addDocument(Document document);
    boolean update(Document document);

    boolean delete(String docId);
    boolean addConnector(Connector connector);

    Document getDocumentByName(String name);
    List<Document> getDocByDocId(String docId);
}
