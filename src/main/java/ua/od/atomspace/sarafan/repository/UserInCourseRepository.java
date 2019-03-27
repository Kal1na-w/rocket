package ua.od.atomspace.sarafan.repository;

import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.sarafan.domain.Course;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.domain.UserInCourse;


import java.util.Set;

public interface UserInCourseRepository extends CrudRepository<UserInCourse,Long> {
    Set<UserInCourse> findAllByCourse(Course course);
    Set<UserInCourse> findAllByUser(User user);
    UserInCourse findByUserAndCourse(User user, Course course);
}
