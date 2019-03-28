package ua.od.atomspace.sarafan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.od.atomspace.sarafan.domain.Course;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.repository.CourseRepository;
import ua.od.atomspace.sarafan.repository.UserInCourseRepository;
import ua.od.atomspace.sarafan.service.UserInCourseService;

@RestController
public class CourseController {

    private final UserInCourseService userInCourseService;
    private final CourseRepository courseRepository;
    private final UserInCourseRepository userInCourseRepository;


    @Autowired
    public CourseController(UserInCourseService userInCourseService, CourseRepository courseRepository, UserInCourseRepository userInCourseRepository) {
        this.userInCourseService = userInCourseService;
        this.courseRepository = courseRepository;
        this.userInCourseRepository = userInCourseRepository;
    }

    @RequestMapping(value = "/api/courses/{id}/join", method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<?> addUser(@AuthenticationPrincipal User user, @PathVariable(value = "id") Course course) {
        if(courseRepository.findById(course.getId()).isPresent() && userInCourseRepository.findByUserAndCourse(user,course) == null) {
            userInCourseService.addUserToCourse(course,user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
