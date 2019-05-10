package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Project;
import ua.od.atomspace.rocket.repository.ProjectRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    private final ProjectRepository projectRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    @PostMapping
    public ResponseEntity <Project> post(@RequestBody Project project) {
        entityManager.persist(project);
        entityManager.flush();
        entityManager.clear();
        return new ResponseEntity<>(project,HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity <Project> put(@PathVariable("id") Long id, @RequestBody Project project) {
        if(projectRepository.findById(id).isPresent()) {
            Project requestProject = entityManager.find(Project.class,id);
            requestProject.setName(project.getName());
            requestProject.setProjectStatus(project.getProjectStatus());
            requestProject.setGithub(project.getGithub());
            entityManager.persist(requestProject);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(requestProject,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
        if(projectRepository.findById(id).isPresent()) {
            Project project = entityManager.find(Project.class,id);
            entityManager.remove(project);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
