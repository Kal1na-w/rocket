package ua.od.atomspace.sarafan.repository;

import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.sarafan.domain.Project;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.domain.UserInProject;


import java.util.Set;

public interface UserInProjectRepository extends CrudRepository<UserInProject,Long> {
    Set<UserInProject> findAllByProject(Project project);
    Set<UserInProject> findAllByUser(User user);
    UserInProject findByUserAndProject(User user, Project project);
}
