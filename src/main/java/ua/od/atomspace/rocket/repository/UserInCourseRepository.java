package ua.od.atomspace.rocket.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInCourse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserInCourseRepository extends CrudRepository<UserInCourse,Long> {
    Set<UserInCourse> findAllByCourse(Course course);
    Set<UserInCourse> findAllByUser(User user);
    UserInCourse findByUserAndCourse(User user, Course course);
    List<UserInCourse> findAll();

}
