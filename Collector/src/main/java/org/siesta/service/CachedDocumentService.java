package org.siesta.service;

import org.siesta.model.Document;
import org.siesta.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * This class
 */
@Service
public class CachedDocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public Document seveUpdateDoc(Document document){
        return documentRepository.save(document); // data jpa delegate it to persist or merge behind scene
    }

    public Document getDoc(Long id){
        return documentRepository.findOne(id);
    }

    public void deleteDocument(Document document){
        documentRepository.delete(document);
    }

    @Transactional
    public void saveDocument(Collection<Document> documents){
        documentRepository.save(documents);
    }




}
