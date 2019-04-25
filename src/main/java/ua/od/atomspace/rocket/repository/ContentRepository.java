package ua.od.atomspace.rocket.repository;

import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.Content;

import java.util.List;

public interface ContentRepository extends CrudRepository<Content,Long> {
    List<Content> findAll();
}
