package ua.od.atomspace.rocket.repository;

import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findAll();

    User findByUsername(String username);
}
