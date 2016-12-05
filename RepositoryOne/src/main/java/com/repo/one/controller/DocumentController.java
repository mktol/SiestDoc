package com.repo.one.controller;

import com.repo.one.model.Document;
import com.repo.one.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
     * @param documentId
     * @param document
     * @return
     */
    @RequestMapping(value = "/rest/documents/{id}", method = RequestMethod.PUT)
    public Document saveConcreateDocument(@PathVariable("id") String documentId,@RequestBody() Document document){
        return documentService.saveDocument(documentId, document);
    }

    @RequestMapping(value = "/rest/documents", method = RequestMethod.POST /*, consumes = {"application/json"}*/)
    public Document saveDocument(@RequestBody(required = false) Document document  ){
        System.out.println("so so os ososo os oso so os oso soso so os oso");
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
            return true; // TODO rewrite this method. Handle exception or ...
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
