package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Role;
import ua.od.atomspace.rocket.domain.RoleInCourse;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInCourse;
import ua.od.atomspace.rocket.repository.UserInCourseRepository;
import ua.od.atomspace.rocket.security.CurrentUser;

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
    public ResponseEntity<UserInCourse> put(@CurrentUser User user, @PathVariable("id") UserInCourse pathUserInCourse, @RequestBody UserInCourse userInCourse) {
        if(!(userInCourseRepository.findByUserAndCourse(user,pathUserInCourse.getCourse()).getRoleInCourse() == RoleInCourse.LEAD) || user.getRoles().contains(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (userInCourseRepository.findById(pathUserInCourse.getId()).isPresent()) {
            UserInCourse requestUserInCourse = entityManager.find(UserInCourse.class, pathUserInCourse.getId());
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

}
