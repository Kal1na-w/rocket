package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.UserInCourse;
import ua.od.atomspace.rocket.repository.UserInCourseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "api/userInCourses")
@CrossOrigin("*")
public class UserInCourseController {
    private final UserInCourseRepository userInCourseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserInCourseController(UserInCourseRepository userInCourseRepository) {
        this.userInCourseRepository = userInCourseRepository;
    }

    // @GetMapping("/byCourse")
    // public ResponseEntity<Set<UserInCourse>> allByCourse(@RequestBody Course
    // course) {
    // Set<UserInCourse> userInCourses =
    // userInCourseRepository.findAllByCourse(course);
    // return new ResponseEntity<>(userInCourses, HttpStatus.OK);
    // }
    //
    // @GetMapping("/byUser")
    // public ResponseEntity<Set<UserInCourse>> allByUser(@RequestBody User user) {
    // Set<UserInCourse> userInCourses = userInCourseRepository.findAllByUser(user);
    // return new ResponseEntity<>(userInCourses, HttpStatus.OK);
    // }

    @GetMapping
    public ResponseEntity<List<UserInCourse>> getAll() {
        return new ResponseEntity<>(userInCourseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInCourse> get(@PathVariable("id") Long id) {
        if (userInCourseRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(userInCourseRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserInCourse> put(@PathVariable("id") Long id, @RequestBody UserInCourse userInCourse) {
        if (userInCourseRepository.findById(id).isPresent()) {
            UserInCourse requestUserInCourse = entityManager.find(UserInCourse.class, id);
            requestUserInCourse.setRoleInCourse(userInCourse.getRoleInCourse());
            requestUserInCourse.setProgress(userInCourse.getProgress());
            entityManager.persist(requestUserInCourse);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(userInCourseRepository.save(requestUserInCourse), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @Transactional
    // @DeleteMapping("/{id}")
    // public ResponseEntity <?> delete(@PathVariable("id") Long id) {
    // if(userInCourseRepository.findById(id).isPresent()) {
    // UserInCourse userInCourse = entityManager.find(UserInCourse.class,id);
    // entityManager.remove(userInCourse);
    // entityManager.flush();
    // entityManager.clear();
    // return new ResponseEntity<>(HttpStatus.OK);
    // }
    // else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }
}
