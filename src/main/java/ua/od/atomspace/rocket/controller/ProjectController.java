package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Project;
import ua.od.atomspace.rocket.repository.ProjectRepository;

import java.util.List;

@RestController
@RequestMapping(value = "api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projects = projectRepository.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity <Project> get(@PathVariable("id") Long id) {
        if(projectRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(projectRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity <Project> post(@RequestBody Project project) {
        return new ResponseEntity<>(projectRepository.save(project),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity <Project> put(@PathVariable("id") Long id, @RequestBody Project project) {
        if(projectRepository.findById(id).isPresent()) {
            Project requestProject = projectRepository.findById(id).get();
            requestProject.setName(project.getName());
            requestProject.setUsers(project.getUsers());
            requestProject.setProjectStatus(project.getProjectStatus());
            requestProject.setGithub(project.getGithub());
            return new ResponseEntity<>(projectRepository.save(requestProject),HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
        if(projectRepository.findById(id).isPresent()) {
            projectRepository.delete(projectRepository.findById(id).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
