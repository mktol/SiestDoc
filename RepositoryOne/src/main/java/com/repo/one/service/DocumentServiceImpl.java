package com.repo.one.service;

import com.repo.one.model.Document;
import com.repo.one.repository.DummyDocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DummyDocumentRepo documentRepo;

    @Override
    public List<Document> getAll() {

        return documentRepo.allDocuments();
    }



    @Override
    public Document getDocument(String name) {
        return documentRepo.getByName(name).get(); //TODO rewrite it to correct code

    }

    @Override
    public Document setDocument(Document document) {
        if(document.getId()==null){
            document.setId(UUID.randomUUID().toString());
        }
        return documentRepo.create(document);
    }

    @Override
    public Document update(Document document) {
        return documentRepo.update(document);
    }

    @Override
    public boolean remove(Document document) {
        return documentRepo.remove(document);
    }

    @Override
    public boolean remove(String documentId) {
        return documentRepo.remove(documentId);

    }


    @Override
    public Document saveDocument(String documentId, Document document) {
        document.setId(documentId);
        documentRepo.saveUpdateByDocumentId(documentId, document);
        return document;
    }

    @Override
    public Document getDocumentByID(String documentId) {
        Optional<Document> doc = documentRepo.getById(documentId);

        return doc.orElseThrow(()->new IllegalArgumentException("wrong id or id does not exist"));
    }
}
