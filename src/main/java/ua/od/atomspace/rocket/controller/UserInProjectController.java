package ua.od.atomspace.rocket.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Project;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInProject;
import ua.od.atomspace.rocket.repository.ProjectRepository;
import ua.od.atomspace.rocket.repository.UserInProjectRepository;
import ua.od.atomspace.rocket.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/userInProjects")
@CrossOrigin("*")
public class UserInProjectController {
    private final UserInProjectRepository userInProjectRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserInProjectController(UserInProjectRepository userInProjectRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.userInProjectRepository = userInProjectRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }


//    @GetMapping("/byProject")
//    public ResponseEntity<Set<UserInProject>> allByProject(@RequestBody Project project) {
//        Set<UserInProject> userInProjects = userInProjectRepository.findAllByProject(project);
//        return new ResponseEntity<>(userInProjects, HttpStatus.OK);
//    }
//
//    @GetMapping("/byUser")
//    public ResponseEntity<Set<UserInProject>> allByUser(@RequestBody User user) {
//        Set<UserInProject> userInProjects = userInProjectRepository.findAllByUser(user);
//        return new ResponseEntity<>(userInProjects, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<UserInProject>> getAll() {
        return new ResponseEntity<>(userInProjectRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <UserInProject> get(@PathVariable("id") Long id) {
        if(userInProjectRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(userInProjectRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @Transactional
//    @PostMapping
//    public ResponseEntity <UserInProject> post(@RequestBody UserInProject userInProject) {
//        entityManager.persist(userInProject);
//        entityManager.flush();
//        entityManager.clear();
//        return new ResponseEntity<>(userInProject,HttpStatus.CREATED);
//    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity <UserInProject> put(@PathVariable("id") Long id, @RequestBody UserInProject userInProject) {
        if(userInProjectRepository.findById(id).isPresent()) {
            UserInProject requestUserInProject = entityManager.find(UserInProject.class,id);
            requestUserInProject.setRoleInProject(userInProject.getRoleInProject());
            requestUserInProject.setProjectSubTeam(userInProject.getProjectSubTeam());
            entityManager.persist(requestUserInProject);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(requestUserInProject,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
//        if(userInProjectRepository.findById(id).isPresent()) {
//            UserInProject userInProject = entityManager.find(UserInProject.class,id);
//            entityManager.remove(userInProject);
//            entityManager.flush();
//            entityManager.clear();
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
