package ua.od.atomspace.rocket.repository;

import org.springframework.data.repository.CrudRepository;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.Level;

import java.util.List;

public interface LevelRepository extends CrudRepository<Level,Long> {
    List<Level> findAll();
    List<Level> findAllByCourseOrderByNumberOfLevelAsc(Course course);
    Level findByCourseAndNumberOfLevel(Course course, int numberOfLevel);
}
