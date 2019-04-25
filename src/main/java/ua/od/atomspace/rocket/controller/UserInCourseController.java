package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInCourse;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.UserInCourseRepository;
import ua.od.atomspace.rocket.repository.UserRepository;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/userInCourses")
@CrossOrigin("*")
public class UserInCourseController {
    private final UserInCourseRepository userInCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UserInCourseController(UserInCourseRepository userInCourseRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.userInCourseRepository = userInCourseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/byCourse")
    public ResponseEntity<Set<UserInCourse>> allByCourse(@RequestBody Course course) {
        Set<UserInCourse> userInCourses = userInCourseRepository.findAllByCourse(course);
        return new ResponseEntity<>(userInCourses, HttpStatus.OK);
    }

    @GetMapping("/byUser")
    public ResponseEntity<Set<UserInCourse>> allByUser(@RequestBody User user) {
        Set<UserInCourse> userInCourses = userInCourseRepository.findAllByUser(user);
        return new ResponseEntity<>(userInCourses, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserInCourse>> getAll() {
        return new ResponseEntity<>(userInCourseRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <UserInCourse> get(@PathVariable("id") Long id) {
        if(userInCourseRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(userInCourseRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity <UserInCourse> post(@RequestBody UserInCourse userInCourse) {
        courseRepository.save(userInCourse.getCourse());
        userRepository.save(userInCourse.getUser());
        return new ResponseEntity<>(userInCourseRepository.save(userInCourse),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity <UserInCourse> put(@PathVariable("id") Long id, @RequestBody UserInCourse userInCourse) {
        if(userInCourseRepository.findById(id).isPresent()) {
            UserInCourse requestUserInCourse = userInCourseRepository.findById(id).get();
            requestUserInCourse.setRoleInCourse(userInCourse.getRoleInCourse());
            requestUserInCourse.setProgress(userInCourse.getProgress());    //todo use clone()
            requestUserInCourse.setContents(userInCourse.getContents());
            return new ResponseEntity<>(userInCourseRepository.save(requestUserInCourse),HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
        if(userInCourseRepository.findById(id).isPresent()) {
            userInCourseRepository.delete(userInCourseRepository.findById(id).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
