package ua.od.atomspace.rocket.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.Level;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.LevelRepository;
import ua.od.atomspace.rocket.service.LevelService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class LevelController {

    private final LevelRepository levelRepository;
    private final CourseRepository courseRepository;
    private final LevelService levelService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LevelController(LevelRepository levelRepository, CourseRepository courseRepository, LevelService levelService) {
        this.levelRepository = levelRepository;
        this.courseRepository = courseRepository;
        this.levelService = levelService;
    }


    @GetMapping("/levels")
    public ResponseEntity<List<Level>> getAll() {
        return new ResponseEntity<>(levelRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/levels/{id}")
    public ResponseEntity<Level> get(@PathVariable Long id) {
        if(levelRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(levelRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
