package com.reposito.two.entity;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * POJO DTO class for heteoas
 */
public class PersonResource extends ResourceSupport {

    private String name;

    @JsonCreator
    public PersonResource(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
