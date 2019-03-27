package ua.od.atomspace.sarafan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.sarafan.domain.Project;


public interface ProjectRepository extends CrudRepository<Project,Long> {
    Iterable<Project> findAll(Sort sort);

    Page<Project> findAll(Pageable pageable);
}
