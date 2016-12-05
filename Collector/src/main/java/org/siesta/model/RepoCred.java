package org.siesta.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class
 */
@Entity
public class RepoCred {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String user;
    private String password;
    private String description;
    @OneToMany(mappedBy = "repository")
    private List<Document> document = new ArrayList<>();

    public Document addDcoument(Document doc){
        if (document.contains(doc)) {
            doc.addRepository(this);
            return doc;
        }
        document.add(doc);
        doc.addRepository(this);
        return doc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }
}
