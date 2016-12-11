package org.siesta.repository;

import org.siesta.model.Repository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */
public interface RepoRepository extends CrudRepository<Repository, Long> {
}
