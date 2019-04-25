package ua.od.atomspace.rocket.repository;


import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.Level;

import java.util.List;


public interface CourseRepository extends CrudRepository<Course,Long> {
    List<Course> findAll();
}
