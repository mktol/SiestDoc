package org.siesta.model;

import javax.persistence.*;

/**
 * Representation of repository
 */
@Entity
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String connectUrl;
    private String name;

    public Repository() {
    }

    public Repository(String connectUrl, String name) {
        this.connectUrl = connectUrl;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }
}
