package com.reposito.two.controller;

import com.reposito.two.entity.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * This class
 */
@RestController
public class DocController {
    @RequestMapping("/docs")
    public ResponseEntity<List<Document>> documents(){
        List<Document> docs = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            docs.add(new Document((long) i, "title "+i, " Content ... "+i));
        }
        return new ResponseEntity<List<Document>>(docs, HttpStatus.OK);

    }
}
