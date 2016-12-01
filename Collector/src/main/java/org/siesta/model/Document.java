package org.siesta.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class
 */
@Entity
public class Document {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String title;

    @ManyToMany
    @JoinTable(name="DOC_INDEXES")
    private Set<Index> indexes = new HashSet<>();

    private String content;
    @OneToMany(mappedBy = "document")
    private List<Comment> comments;

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

    public Set<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(Set<Index> indexes) {
        this.indexes = indexes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
