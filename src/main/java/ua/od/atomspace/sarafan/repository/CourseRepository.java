package ua.od.atomspace.sarafan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.sarafan.domain.Course;


public interface CourseRepository extends CrudRepository<Course,Long> {
    Iterable<Course> findAll(Sort sort);

    Page<Course> findAll(Pageable pageable);
}
