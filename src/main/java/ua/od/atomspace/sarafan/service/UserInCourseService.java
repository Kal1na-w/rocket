package ua.od.atomspace.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.od.atomspace.sarafan.domain.Course;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.domain.UserInCourse;
import ua.od.atomspace.sarafan.repository.CourseRepository;
import ua.od.atomspace.sarafan.repository.UserInCourseRepository;
import ua.od.atomspace.sarafan.repository.UserRepository;

@Service
public class UserInCourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserInCourseRepository userInCourseRepository;


    @Autowired
    public UserInCourseService(CourseRepository courseRepository, UserRepository userRepository, UserInCourseRepository userInCourseRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.userInCourseRepository = userInCourseRepository;
    }

    public void addUserToCourse(Course course, User user) {
        Course courseForUser = courseRepository.findById(course.getId()).get();
        User userForCourse = userRepository.findByUsername(user.getUsername());
        UserInCourse userInCourse = new UserInCourse();
        userInCourse.setCourse(courseForUser);
        userInCourse.setUser(userForCourse);
        userInCourseRepository.save(userInCourse);
        userForCourse.getCourses().add(userInCourseRepository.findByUserAndCourse(user,course));
        courseForUser.getUsers().add(userInCourseRepository.findByUserAndCourse(user,course));
        userRepository.save(userForCourse);
        courseRepository.save(courseForUser);
    }
}
