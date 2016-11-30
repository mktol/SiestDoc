package org.siesta.service;

import java.util.List;

/**
 * This class
 */
public class DocListWrapper {
    private String name;
    private List<Document> documents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
