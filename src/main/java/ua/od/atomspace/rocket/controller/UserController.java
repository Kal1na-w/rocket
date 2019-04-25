package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.ProjectRepository;
import ua.od.atomspace.rocket.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin("*")
public class UserController {

    private final UserRepository userRepository;

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
    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> put(@PathVariable("id") Long id, @RequestBody User user) {
        if(userRepository.findById(id).isPresent()) {
            User requestUser = userRepository.findById(id).get();
            requestUser.setEmail(user.getEmail());
            requestUser.setTelegram(user.getTelegram());
            requestUser.setRoles(user.getRoles());
            requestUser.setGithub(user.getGithub());
            requestUser.setFirstName(user.getFirstName());
            requestUser.setLastName(user.getLastName());
            return new ResponseEntity<>(userRepository.save(requestUser),HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.delete(userRepository.findById(id).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
