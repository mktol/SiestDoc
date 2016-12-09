package org.siesta.repository;

import org.siesta.model.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * This class
 */
public interface DocumentRepository extends CrudRepository<Document, Long> {

    void deleteByDocId(String docId);
    Optional<Document> findDocumentByName(String name);
}
