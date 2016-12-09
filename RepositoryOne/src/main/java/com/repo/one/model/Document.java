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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (id != null ? !id.equals(document.id) : document.id != null) return false;
        if (name != null ? !name.equals(document.name) : document.name != null) return false;
        if (title != null ? !title.equals(document.title) : document.title != null) return false;
        return content != null ? content.equals(document.content) : document.content == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
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
