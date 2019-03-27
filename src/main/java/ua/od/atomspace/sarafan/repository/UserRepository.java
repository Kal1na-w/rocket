package ua.od.atomspace.sarafan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.sarafan.domain.User;


public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);

    Iterable<User> findAll(Sort sort);

    Page<User> findAll(Pageable pageable);
}
