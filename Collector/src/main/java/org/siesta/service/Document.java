package org.siesta.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    private Long id;
    private String name;
    private String title;
    private String content;
    private String metadata;

    public Document() {
    }

    public Document(Long id, String name, String title, String content) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
