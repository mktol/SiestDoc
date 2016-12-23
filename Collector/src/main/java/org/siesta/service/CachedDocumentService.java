package org.siesta.service;

import org.siesta.error.DocumentNotFindException;
import org.siesta.model.Document;
import org.siesta.repository.DocumentRepoImpl;
import org.siesta.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This is cache service for document
 */
@Service
public class CachedDocumentService {

    private final Logger logger = LoggerFactory.getLogger(CachedDocumentService.class);

    @Autowired
    private DocumentRepoImpl documentRepo;

    @Autowired
    private DocumentRepository documentRepository;

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
    public List<Document> saveDocument(List<Document> documents){
        return documentRepo.merge(documents);
/*        for (Document document : documents) {
            documentRepository.save(document);
        }*/
    }

    public List<Document> getAll(){
        return (List<Document>) documentRepository.findAll();
    }

    public Document getDocumentByName(String name) {
        logger.debug("find document with name "+name);
        return  documentRepository.findDocumentByName(name).orElseThrow(DocumentNotFindException::new);
    }

    public Document getByDocumentId(String docId) {
        return documentRepository.findDocumentByDocId(docId).orElse(null);
    }
}
