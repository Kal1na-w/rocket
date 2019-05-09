package ua.od.atomspace.rocket.repository;

import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.Project;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findAll();
}
