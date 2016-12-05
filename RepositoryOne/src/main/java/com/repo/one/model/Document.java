package com.repo.one.model;

/**
 * This class
 */
public class Document {

    private String id;
    private String name;
    private String title;
    private String content;
    private String metadata;

    public Document() {
    }

    public Document(String id, String name, String title, String content) {
        this(name, title, content);
        this.id = id;
    }

    public Document(String name, String title, String content) {
        this.name = name;
        this.title = title;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
