package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.ProjectRepository;
import ua.od.atomspace.rocket.repository.UserRepository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

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
        if(userRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(userRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<User> post(@RequestBody User user) {
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<User> put(@PathVariable("id") Long id, @RequestBody User user) {
        if(userRepository.findById(id).isPresent()) {
            User requestUser = entityManager.find(User.class,id);
            requestUser.setEmail(user.getEmail());
            requestUser.setTelegram(user.getTelegram());
            requestUser.setRoles(user.getRoles());
            requestUser.setGithub(user.getGithub());
            requestUser.setFirstName(user.getFirstName());
            requestUser.setLastName(user.getLastName());
            entityManager.persist(requestUser);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(userRepository.save(requestUser),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
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
