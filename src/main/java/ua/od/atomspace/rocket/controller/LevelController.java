package ua.od.atomspace.rocket.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.*;
import ua.od.atomspace.rocket.repository.LevelRepository;
import ua.od.atomspace.rocket.repository.UserInCourseRepository;
import ua.od.atomspace.rocket.security.CurrentUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/levels")
@CrossOrigin("*")
public class LevelController {

    private final LevelRepository levelRepository;
    private final UserInCourseRepository userInCourseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LevelController(LevelRepository levelRepository, UserInCourseRepository userInCourseRepository) {
        this.levelRepository = levelRepository;
        this.userInCourseRepository = userInCourseRepository;
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
    public ResponseEntity<Level> put(@CurrentUser User user, @PathVariable("id") Level pathLevel, @RequestBody Level level) {
        if(!(userInCourseRepository.findByUserAndCourse(user,pathLevel.getCourse()).getRoleInCourse() == RoleInCourse.LEAD) || user.getRoles().contains(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(levelRepository.findById(pathLevel.getId()).isPresent()) {
            Level requestLevel = entityManager.find(Level.class,pathLevel.getId());
            requestLevel.setName(level.getName());
            requestLevel.setContents(level.getContents());
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
