package com.repo.one.model;

import java.util.List;

/**
 * This class
 */
public class Document {

    private String docId;
    private String name;
    private String title;
    private String content;
    private List<Comment> comments;

    public Document() {
    }

    public Document(String docId, String name, String title, String content) {
        this(name, title, content);
        this.docId = docId;
    }

    public Document(String name, String title, String content) {
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (docId != null ? !docId.equals(document.docId) : document.docId != null) return false;
        if (name != null ? !name.equals(document.name) : document.name != null) return false;
        if (title != null ? !title.equals(document.title) : document.title != null) return false;
        return content != null ? content.equals(document.content) : document.content == null;

    }

    @Override
    public int hashCode() {
        int result = docId != null ? docId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docId='" + docId + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
