package org.siesta.repository;

import org.siesta.model.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This class
 */
@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
}
