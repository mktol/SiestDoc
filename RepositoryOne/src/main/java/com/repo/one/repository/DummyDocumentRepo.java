package com.repo.one.repository;

import com.repo.one.model.Document;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Dummy document repo. Contain basic CRUD functional
 */

@Component
@Scope("singleton")
public class DummyDocumentRepo { // TODO CRUD must be rewritten !!!

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AtomicLong counter = new AtomicLong();

    private List<Document> documents = new ArrayList<>();

    public DummyDocumentRepo() {
        documents = initDocs();
    }

    private List<Document> initDocs() {
        List<Document> docs = new ArrayList<>();
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String name = "name";
        for (int i = 0; i < 20; i++) {
            String title = RandomStringUtils.random(length, useLetters, useNumbers);
            String content = RandomStringUtils.random(length*50, useLetters, useNumbers);
            docs.add(new Document(UUID.randomUUID().toString(), name+counter.incrementAndGet(), title, content));
        }
        docs.add(new Document("5dd10405-e6e5-4bf9-8256-d75b45bb0596", "name", "title", "Some content"));

        logger.info(docs.size()+" documents are initialized.");

        return docs;
    }

    public List<Document> allDocuments(){
        return new ArrayList<>(documents);
    }

    public Optional<Document> getByName(final String name){

        return documents.stream().filter(d->d.getName().equals(name)).findFirst();
    }

    public Document create(Document document){
        if(document!=null){
            document.setDocId(UUID.randomUUID().toString());
            documents.add(document);
            logger.debug("document with Id "+document.getDocId()+" is created");
            return document;
        }else{
            logger.error("document  "+document+" cant be created");
            throw new IllegalArgumentException("Document can not be null");
        }
    }

    public boolean remove(String id){
        logger.debug("document with id "+id+" is removed");
        return documents.removeIf(document -> document.getDocId().equals(id));
    }


    public void saveUpdateByDocumentId(String documentId, Document document) {
        if(document==null){
            throw new  IllegalArgumentException("wrong data: docId = "+documentId+", document = "+document);
        }
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getDocId().equals(documentId)){
                documents.set(i, document);
                logger.debug("document id = "+ documentId+" is updated");
                return;
            }
        }
        documents.add(document);
        logger.debug("document id = "+ documentId+" is new for repository and is added.");
    }

    public Optional<Document> getById(String docId) {
        return documents.stream().filter(document -> document.getDocId().equals(docId)).findAny();
    }
}
