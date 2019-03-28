package ua.od.atomspace.sarafan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ua.od.atomspace.sarafan.domain.Project;

@RepositoryRestResource
public interface ProjectRepository extends CrudRepository<Project,Long> {

}
