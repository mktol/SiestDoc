package com.repo.one.service;

import com.repo.one.model.Document;
import com.repo.one.repository.DummyDocumentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private DummyDocumentRepo documentRepo;

    @Override
    public List<Document> getAll() {

        return documentRepo.allDocuments();
    }



    @Override
    public Document getDocument(String name) {
        logger.info("get element with name " + name);
        Document document = documentRepo.getByName(name).orElse(null);
        return document; //TODO rewrite it to correct code

    }

    @Override
    public Document setDocument(Document document) {
        if(document.getId()==null){
            document.setId(UUID.randomUUID().toString());
            logger.debug("document id "+document.getId()+" is generated. ");
        }
        return documentRepo.create(document);
    }

    @Override
    public Document update(Document document) {
        documentRepo.saveUpdateByDocumentId(document.getId(), document);
        return document;
    }

    @Override
    public boolean remove(Document document) {
        return documentRepo.remove(document.getId());
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
