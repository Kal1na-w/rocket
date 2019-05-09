package ua.od.atomspace.rocket.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Level;
import ua.od.atomspace.rocket.repository.LevelRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/levels")
@CrossOrigin("*")
public class LevelController {

    private final LevelRepository levelRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LevelController(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }


    @GetMapping
    public ResponseEntity<List<Level>> getAll() {
        return new ResponseEntity<>(levelRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> get(@PathVariable Long id) {
        if(levelRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(levelRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Level> put(@PathVariable Long id,@RequestBody Level level) {
        if(levelRepository.findById(id).isPresent()) {
            Level requestLevel = entityManager.find(Level.class,id);
            requestLevel.setName(level.getName());
            entityManager.persist(requestLevel);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(requestLevel,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
