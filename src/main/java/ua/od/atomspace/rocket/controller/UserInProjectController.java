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

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/userInProjects")
@CrossOrigin("*")
public class UserInProjectController {
    private final UserInProjectRepository userInProjectRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public UserInProjectController(UserInProjectRepository userInProjectRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.userInProjectRepository = userInProjectRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }


    @GetMapping("/byProject")
    public ResponseEntity<Set<UserInProject>> allByProject(@RequestBody Project project) {
        Set<UserInProject> userInProjects = userInProjectRepository.findAllByProject(project);
        return new ResponseEntity<>(userInProjects, HttpStatus.OK);
    }

    @GetMapping("/byUser")
    public ResponseEntity<Set<UserInProject>> allByUser(@RequestBody User user) {
        Set<UserInProject> userInProjects = userInProjectRepository.findAllByUser(user);
        return new ResponseEntity<>(userInProjects, HttpStatus.OK);
    }

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
    @PostMapping
    public ResponseEntity <UserInProject> post(@RequestBody UserInProject userInProject) {
        userRepository.save(userInProject.getUser());
        projectRepository.save(userInProject.getProject());
        return new ResponseEntity<>(userInProjectRepository.save(userInProject),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity <UserInProject> put(@PathVariable("id") Long id, @RequestBody UserInProject userInProject) {
        if(userInProjectRepository.findById(id).isPresent()) {
            UserInProject requestUserInProject = userInProjectRepository.findById(id).get();
            requestUserInProject.setRoleInProject(userInProject.getRoleInProject());
            requestUserInProject.setProject(userInProject.getProject());
            requestUserInProject.setUser(userInProject.getUser());
            requestUserInProject.setProjectSubTeam(userInProject.getProjectSubTeam());
            userRepository.save(userInProject.getUser());
            projectRepository.save(userInProject.getProject());
            return new ResponseEntity<>(requestUserInProject,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
        if(userInProjectRepository.findById(id).isPresent()) {
            userInProjectRepository.delete(userInProjectRepository.findById(id).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
