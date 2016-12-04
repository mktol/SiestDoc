package com.repo.one.controller;

import com.repo.one.model.Document;
import com.repo.one.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController()
public class  DocumentController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/rest/documents", method = RequestMethod.GET)
    public List<Document> documents(@RequestParam(value = "docId", required = false)String documentId){
        if(documentId==null) {
            return documentService.getAll();
        }
        List<Document> doc = new ArrayList<>();
        doc.add(documentService.getDocumentByID(documentId));
          return doc; // TODO should return optional
    }

/*    @RequestMapping("/rest/documents/{name}") // TODO not restfulll
    public Document document(@PathVariable("name")String name){
        return documentService.getDocument(name);
    }*/

    /**
     * Save document in with concrete id
     * @param documentid
     * @param document
     * @return
     */
    @RequestMapping(value = "/rest/documents/{id}", method = RequestMethod.PUT)
    public Document saveConcreateDocument(@PathVariable("id") String documentid,@RequestBody() Document document){
        return documentService.saveDocument(documentid, document);
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

    @RequestMapping(value = "/rest/documents/{id}", method = RequestMethod.DELETE)
    public boolean deleteDocument(@PathVariable("id") String documentId){
        return documentService.remove(documentId);
    }
/*    @RequestMapping(value = "/rest/documents/", method = RequestMethod.GET)
    public Document getDocumentByDocumentId(@RequestParam(value = "docId", required = false)String documentId){
        return documentService.getDocumentByID(documentId); // TODO should return optional
    }*/



}
