package org.siesta.repository;

import org.siesta.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This class
 */
@Repository
public interface CommetRepository extends CrudRepository<Comment , Long> {
}
