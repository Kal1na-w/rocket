package ua.od.atomspace.rocket.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    List<User> findAll();
}