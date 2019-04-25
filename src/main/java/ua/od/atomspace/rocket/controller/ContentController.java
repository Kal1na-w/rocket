package ua.od.atomspace.rocket.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Content;
import ua.od.atomspace.rocket.repository.ContentRepository;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.LevelRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/contents")
@CrossOrigin("*")
public class ContentController {
    private final CourseRepository courseRepository;
    private final LevelRepository levelRepository;
    private final ContentRepository contentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ContentController(CourseRepository courseRepository, LevelRepository levelRepository, ContentRepository contentRepository) {
        this.courseRepository = courseRepository;
        this.levelRepository = levelRepository;
        this.contentRepository = contentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Content>> getAll() {
        return new ResponseEntity<>(contentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> get(@PathVariable Long id) {
        if(contentRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(contentRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
