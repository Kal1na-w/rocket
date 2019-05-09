package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Content;
import ua.od.atomspace.rocket.repository.ContentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/contents")
@CrossOrigin("*")
public class ContentController {
    private final ContentRepository contentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ContentController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Content>> getAll() {
        return new ResponseEntity<>(contentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> get(@PathVariable Long id) {
        if (contentRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(contentRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Content> put(@PathVariable Long id, @RequestBody Content content) {
        if (contentRepository.findById(id).isPresent()) {
            Content requestContent = entityManager.find(Content.class, id);
            requestContent.setContext(content.getContext());
            entityManager.persist(requestContent);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(requestContent, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
