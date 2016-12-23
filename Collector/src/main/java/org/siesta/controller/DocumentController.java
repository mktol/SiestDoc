package org.siesta.controller;

import org.siesta.model.Document;
import org.siesta.service.HandleDocumentService;
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

    private static final String DOCUMENTS_PATH = "/documents";
    private static final String DOC_ID_PATH_VAR = "/{docId}";
    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    private HandleDocumentService handleDocumentService;

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Document> getAllDocuments() {
        return handleDocumentService.getAll();
    }

    @RequestMapping(value = DOCUMENTS_PATH+DOC_ID_PATH_VAR, method = RequestMethod.GET)
    public
    @ResponseBody Document getDocumentById(@PathVariable(value = "docId", required = false) String docId) {
        return handleDocumentService.getDocumentById(docId);
    }

    @RequestMapping(value = "/documents/{docId}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteDocument(@PathVariable(value = "docId", required = false) String docId) {
        Boolean res = handleDocumentService.deleteDocument(docId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateDocument(@RequestBody Document document) {
        Boolean isUpdated = handleDocumentService.updateDocument(document);
        if(isUpdated){
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(isUpdated, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    public ResponseEntity<Document> saveDocument(@RequestBody(required = false) Document document) {
        Document savedDocument = handleDocumentService.addDocument(document);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

}
