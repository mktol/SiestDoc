package com.repo.one.service;

import com.repo.one.model.Document;

import java.util.List;


public interface DocumentService {

    List<Document> getAll();
    Document getDocument(String name);
    Document setDocument(Document document);
    Document update(Document document);
    boolean remove(Document document);
    boolean remove(String documentId);

    Document saveDocument(String position, Document document);

    Document getDocumentByID(String documentId);
}
