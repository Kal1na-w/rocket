package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Content;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.Level;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.LevelRepository;
import ua.od.atomspace.rocket.service.LevelService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "api/courses")
@CrossOrigin("*")
public class CourseController {

    private final CourseRepository courseRepository;
    private final LevelRepository levelRepository;
    private final LevelService levelService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CourseController(CourseRepository courseRepository, LevelRepository levelRepository, LevelService levelService) {
        this.courseRepository = courseRepository;
        this.levelRepository = levelRepository;
        this.levelService = levelService;
    }

    @GetMapping public ResponseEntity <List<Course>> getAll() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity <Course> get(@PathVariable("id") Long id) {
        if(courseRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(courseRepository.findById(id).get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity <Course> post(@RequestBody Course course) {
        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity <Course> put(@PathVariable("id") Long id, @RequestBody Course course) {
        if(courseRepository.findById(id).isPresent()) {
            Course requestCourse = entityManager.find(Course.class,id);
            requestCourse.setName(course.getName());
            requestCourse.setPriority(course.getPriority());
            entityManager.persist(requestCourse);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(requestCourse,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Long id) {
        if(courseRepository.findById(id).isPresent()) {
            Course course = entityManager.find(Course.class,id);
            entityManager.remove(course);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/{courseId}/levels")
    public ResponseEntity<Level> postLevelToCourse(@PathVariable Long courseId,@RequestBody Level level) {
        if(courseRepository.findById(courseId).isPresent()) {
            level.setNumberOfLevel(levelRepository.findAllByCourseOrderByNumberOfLevelAsc(courseRepository.findById(courseId).get()).size()+1);
            level.setCourse(courseRepository.findById(courseId).get());
            Course course = entityManager.find(Course.class,courseId);
            int index = course.getLevels().size();
            course.getLevels().add(index,level);
            entityManager.persist(course);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(level,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @Transactional
//    @PutMapping("/{courseId}/levels/{numberOfLevel}")
//    public ResponseEntity<Level> putLevelToCourse(@PathVariable Long courseId,@PathVariable int numberOfLevel,@RequestBody Level level) {
//        if(courseRepository.findById(courseId).isPresent()) {
//            Level requestLevel = entityManager.find(
//                    Level.class,
//                    levelRepository.findByCourseAndNumberOfLevel(courseRepository.findById(courseId).get(),numberOfLevel).getId()
//            );
//            requestLevel.setName(level.getName());
//            entityManager.persist(requestLevel);
//            entityManager.flush();
//            entityManager.clear();
//            return new ResponseEntity<>(requestLevel,HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("{courseId}/levels/{numberOfLevel}")
    public ResponseEntity<Level> getLevelByCourse(@PathVariable Long courseId,@PathVariable int numberOfLevel) {
        if(courseRepository.findById(courseId).isPresent()) {
            return new ResponseEntity<>(levelRepository.findByCourseAndNumberOfLevel(courseRepository.findById(courseId).get(),numberOfLevel),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{courseId}/levels")
    public ResponseEntity<List<Level>> getAllLevelsByCourse(@PathVariable Long courseId) {
        if(courseRepository.findById(courseId).isPresent()) {
            return new ResponseEntity<>(levelRepository.findAllByCourseOrderByNumberOfLevelAsc(courseRepository.findById(courseId).get()),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("{courseId}/levels/{levelId}/contents")
    public ResponseEntity<Content> postContentByLvl(@PathVariable Long courseId, @PathVariable Long levelId, @RequestBody Content content){
        if(courseRepository.findById(courseId).isPresent()) {
            if(levelRepository.findById(levelId).isPresent()) {
                content.setLevel(levelRepository.findById(levelId).get());
                entityManager.persist(content);
                entityManager.flush();
                entityManager.clear();
                return new ResponseEntity<>(content,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/{courseId}/levels/{levelId}/contents/{contentId}")
    public ResponseEntity<?> deleteContentByLvl(@PathVariable Long courseId, @PathVariable Long levelId, @PathVariable Long contentId){
        if(courseRepository.findById(courseId).isPresent()) {
            if(levelRepository.findById(levelId).isPresent()) {
                Level level = entityManager.find(Level.class,levelId);
                level.getContents().remove(entityManager.find(Content.class,contentId));
                entityManager.persist(level);
                entityManager.flush();
                entityManager.clear();
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    @DeleteMapping("/{courseId}/levels/{numberOfLevel}")
    public ResponseEntity<Level> deleteLevelByCourse(@PathVariable Long courseId,@PathVariable int numberOfLevel) {
        if(courseRepository.findById(courseId).isPresent()) {
            levelService.deleteAndReplace(courseRepository.findById(courseId).get(),numberOfLevel);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
