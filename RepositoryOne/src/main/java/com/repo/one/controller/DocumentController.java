package com.repo.one.controller;

import com.repo.one.model.Document;
import com.repo.one.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController()
public class DocumentController {

    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/rest/documents", method = RequestMethod.GET)
    public List<Document> documents(){
        return documentService.getAll();
    }

    @RequestMapping("/{name}") // TODO not restfulll
    public Document document(@PathVariable("name")String name){
        return documentService.getDocument(name);
    }

    /**
     * Save document in concrete position
     * @param position
     * @param document
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Document saveConcreateDocument(@PathVariable("id") Long position,@RequestBody() Document document){
        return documentService.saveDocument(position, document);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    public Document saveDocument(@RequestBody() Document document){

        return documentService.setDocument(document);

    }

    /**
     * Update document
     * @param document
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = {"application/json"})
    public boolean updateDocument(@RequestBody() Document document){
        if(document!=null){
            documentService.update(document);
            return true; // TODO rewritte this method. Handle exeption or ...
        }
        return false;
    }



}
