package com.repo.one.service;

import com.repo.one.model.Document;

import java.util.List;


public interface DocumentService {

    List<Document> getAll();
    Document getDocument(Long id);
    Document getDocument(String name);
    Document setDocument(Document document);
    Document update(Document document);
    boolean remove(Document document);

    Document saveDocument(Long position, Document document);
}
