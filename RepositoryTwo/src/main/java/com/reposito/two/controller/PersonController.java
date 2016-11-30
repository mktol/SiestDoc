package com.reposito.two.controller;

import com.reposito.two.entity.PersonResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * This class
 */
@RestController
public class PersonController {
    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/persons")
    public HttpEntity<PersonResource> persons( @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        PersonResource personResource = new PersonResource(String.format(TEMPLATE, name));
        personResource.add(linkTo(methodOn(PersonController.class).persons(name)).withSelfRel());
        return new ResponseEntity<PersonResource>(personResource, HttpStatus.OK);
    }
}
