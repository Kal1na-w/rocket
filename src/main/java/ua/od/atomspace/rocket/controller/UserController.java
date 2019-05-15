package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Role;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInCourse;
import ua.od.atomspace.rocket.domain.UserInProject;
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

    @GetMapping("/current/projects")
    public ResponseEntity<Set<UserInProject>> getProjectsForCurUser(@CurrentUser User user) {
        return new ResponseEntity<>(user.getProjects(),HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> currentUser(@CurrentUser User user) {
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/current/edit")
    public ResponseEntity<User> putCurrent(@CurrentUser User curUser,@RequestBody User user) {
            return put(user,curUser);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(@CurrentUser User curUser,@PathVariable("id") User pathUser, @RequestBody User user) {
        if(!curUser.getRoles().contains(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (userRepository.findById(pathUser.getId()).isPresent()) {
           return put(user,pathUser);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser User curUser,@PathVariable("id") User pathUser) {
        if(!curUser.getRoles().contains(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (userRepository.findById(pathUser.getId()).isPresent()) {
            User user = entityManager.find(User.class, pathUser.getId());
            entityManager.remove(user);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<User> put(User user,User pathUser) {
        User requestUser = entityManager.find(User.class, pathUser.getId());
        requestUser.setEmail(user.getEmail());
        requestUser.setTelegram(user.getTelegram());
        requestUser.setRoles(user.getRoles());
        requestUser.setGithub(user.getGithub());
        requestUser.setFirstName(user.getFirstName());
        requestUser.setLastName(user.getLastName());
        entityManager.persist(requestUser);
        entityManager.flush();
        entityManager.clear();
        return new ResponseEntity<>(requestUser, HttpStatus.OK);
    }
}
