package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInCourse;
import ua.od.atomspace.rocket.repository.UserRepository;
import ua.od.atomspace.rocket.security.CurrentUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin("*")
public class UserController {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> all() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Long id) {
        if (userRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current/courses")
    public ResponseEntity<Set<UserInCourse>> getCoursesForCurUser(@CurrentUser User user) {
        return new ResponseEntity<>(user.getCourses(),HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> currentUser(@CurrentUser User user) {
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<User> put(@PathVariable("id") Long id, @RequestBody User user) {
        if (userRepository.findById(id).isPresent()) {
            User requestUser = entityManager.find(User.class, id);
            requestUser.setEmail(user.getEmail());
            requestUser.setTelegram(user.getTelegram());
            requestUser.setRoles(user.getRoles());
            requestUser.setGithub(user.getGithub());
            requestUser.setFirstName(user.getFirstName());
            requestUser.setLastName(user.getLastName());
            entityManager.persist(requestUser);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(userRepository.save(requestUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
