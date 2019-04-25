package ua.od.atomspace.rocket.repository;


import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.Project;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInProject;

import java.util.List;
import java.util.Set;

public interface UserInProjectRepository extends CrudRepository<UserInProject,Long> {
    Set<UserInProject> findAllByProject(Project project);
    Set<UserInProject> findAllByUser(User user);
    UserInProject findByUserAndProject(User user, Project project);
    List<UserInProject> findAll();
}
