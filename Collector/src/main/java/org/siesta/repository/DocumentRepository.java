package org.siesta.repository;

import org.siesta.model.Document;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Document repo crud
 */
@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
    @Modifying
    @Transactional
    @Query("delete from Document d where d.docId = ?1")
    void deleteByDocId(String docId);
    Optional<Document> findDocumentByName(String name);
    Optional<Document> findDocumentByDocId(String docId);
//    Optional<Document> findByDocId

}
