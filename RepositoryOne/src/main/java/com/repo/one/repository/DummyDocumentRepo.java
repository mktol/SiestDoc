package com.repo.one.repository;

import com.repo.one.model.Document;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Dummy document repo. Contain basic CRUD functional
 */

@Component
@Scope("singleton")
public class DummyDocumentRepo { // TODO CRUD must be rewritten !!!
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
            docs.add(new Document(counter.incrementAndGet(), name+counter.get(), title, content));
//            docs.add(new Document((long)i, name+counter.get(), title, content));
        }
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
            document.setId(counter.incrementAndGet());
            documents.add(document);
            return document;
        }else{
            throw new IllegalArgumentException("Document can not be null");
        }
    }

    public boolean remove(Document document){
        return documents.remove(document);

    }

    public void remove(Long id){
        documents.removeIf(document -> document.getId().equals(id));
    }

    public Document update(Document document){ // TODO WRONG LOGIC
        Optional<Document> doc = documents.stream().filter(d->d.getId().equals(document.getId())).findFirst();
        Document upd = doc.orElseThrow(()->new IllegalArgumentException("Document is not present in storage.Id= "+document.getId()));
        upd = document;
        return document;
    }

    public void saveUpdatePosition(Long position, Document document) {
        if(position<=0 || document==null){
            throw new  IllegalArgumentException("wrong data: position = "+position+", document = "+document);
        }
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getId().equals(position)){
                documents.add(i, document);
            }else{
                documents.add(document);
            }
        }

    }
}
