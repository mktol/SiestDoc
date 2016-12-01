package org.siesta.repository;

import org.siesta.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This class
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
