package org.siesta.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.siesta.model.Document;
import org.siesta.service.OneDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class
 */
@Controller
public class DocumentController {

    @Autowired
    private OneDocumentService documentService;

    @RequestMapping(value = "/documents",  method = RequestMethod.GET)
    public @ResponseBody List<Document> getAllDocuments(@RequestParam(value = "docId", required = false)String docId){
        if(docId==null) {
            try {
                return documentService.getAllDocuments();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return documentService.getDocByDocId(docId);
    }

    @RequestMapping("/document")
    public @ResponseBody Document findDocByName(@RequestParam(value = "name", required = false) String name){
        return documentService.getDocumentByName(name);
    }

    @RequestMapping(value = "/documents/{docId}", method = RequestMethod.GET)
    public @ResponseBody List<Document> getDocumentById(@PathVariable(value = "docId", required = false)String docId){
        List<Document> docs = documentService.getDocByDocId(docId);
        return docs;
    }

    @RequestMapping(value = "/documents/{docId}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteDocument(@PathVariable(value = "docId", required = false)String docId){
        Boolean res = documentService.delete(docId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateDocument(@RequestBody Document document){
        Boolean isUpdated = documentService.update(document);
        return new ResponseEntity<>(isUpdated, HttpStatus.OK);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    public ResponseEntity<Document> saveDocument(@RequestBody( required = false) Document document){
        Document savedDocument = documentService.addDocument(document);
        return new ResponseEntity<>(savedDocument, HttpStatus.OK);
    }

}
