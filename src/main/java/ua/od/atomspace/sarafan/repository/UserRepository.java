package ua.od.atomspace.sarafan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ua.od.atomspace.sarafan.domain.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
