package org.siesta.repository;

import org.siesta.model.Index;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This class
 */
@Repository
public interface IndexRepository extends CrudRepository<Index, Long> {

}
