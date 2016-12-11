package org.siesta.controller;

import org.siesta.model.Document;
import org.siesta.service.ManageDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class
 */
@Controller
public class DocumentController {

    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private ManageDocumentService documentService;
//TODO make human remove docId from param
    @RequestMapping(value = "/documents",  method = RequestMethod.GET)
    public @ResponseBody List<Document> getAllDocuments(@RequestParam(value = "docId", required = false)String docId){
        if(docId==null) {
                logger.info("get all documents");
                return documentService.getAllDocuments();
        }

        return documentService.getDocByDocId(docId);
    }

    @RequestMapping("/documents")
    public @ResponseBody Document findDocByName(@RequestParam(value = "name") String name){
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
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

}
