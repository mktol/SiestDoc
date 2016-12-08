package org.siesta.service;

import org.siesta.model.Document;
import org.siesta.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * This class
 */
@Service
public class CachedDocumentService {

    private final Logger logger = LoggerFactory.getLogger(CachedDocumentService.class);

    @Autowired
    private DocumentRepository documentRepository;

    public Document saveUpdateDoc(Document document){
        logger.debug("Save document "+ document+" to local db");
        return documentRepository.save(document); // data jpa delegate it to persist or merge behind scene
    }

    public Document getDoc(Long id){
        logger.debug("find document by id "+ id);
        return documentRepository.findOne(id);
    }

    public void deleteDocument(Document document){
        logger.debug("delete document  "+ document);
        documentRepository.delete(document);
    }

    public void deleteDocument(String docId){
        documentRepository.deleteByDocId(docId);
        logger.debug("delete document  "+ docId);
    }

    @Transactional
    public void saveDocument(Collection<Document> documents){
        documentRepository.save(documents);
    }




}
