package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.Content;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.Level;
import ua.od.atomspace.rocket.domain.RoleInCourse;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.domain.UserInCourse;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.LevelRepository;
import ua.od.atomspace.rocket.repository.UserInCourseRepository;
import ua.od.atomspace.rocket.security.CurrentUser;
import ua.od.atomspace.rocket.service.LevelService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/courses")
@CrossOrigin("*")
public class CourseController {

    private final CourseRepository courseRepository;
    private final LevelRepository levelRepository;
    private final LevelService levelService;
    private final UserInCourseRepository userInCourseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CourseController(CourseRepository courseRepository, LevelRepository levelRepository, LevelService levelService, UserInCourseRepository userInCourseRepository) {
        this.courseRepository = courseRepository;
        this.levelRepository = levelRepository;
        this.levelService = levelService;
        this.userInCourseRepository = userInCourseRepository;
    }


    /**
     *  UserInCourse endpoints
     */
     @Transactional
     @PostMapping("/{id}")
     public ResponseEntity<?> joinUserToCourse(@PathVariable(name = "id") Course course,@CurrentUser User user) {
        if(!userInCourseRepository.existsByCourseAndUser(course, user)) {
            UserInCourse userInCourse = new UserInCourse();
            userInCourse.setCourse(course);
            userInCourse.setUser(entityManager.find(User.class, user.getId()));
            userInCourse.setProgress(0);
            userInCourse.setRoleInCourse(RoleInCourse.USER);
            entityManager.persist(userInCourse);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }

     @GetMapping("/{id}/users")
     public ResponseEntity<Set<UserInCourse>> getUsers(@PathVariable(name = "id") Course course) {
         return new ResponseEntity<>(course.getUsers(),HttpStatus.OK);
     }


    /**
     * Course endpoints 
     */
    @GetMapping 
    public ResponseEntity <List<Course>> getAll() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Course> get(@PathVariable("id") Long id) {
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

    /**
     * Level endpoints
     */

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

    /**
     * Content endpoints
     */

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

    @GetMapping("/{courseId}/levels/{levelId}/contents")
    public ResponseEntity<List<Content>> getContentByLevel(@PathVariable Long courseId,@PathVariable Long levelId) {
        if(courseRepository.findById(courseId).isPresent()) {
            if(levelRepository.findById(levelId).isPresent()) {
                return new ResponseEntity<>(levelRepository.findById(levelId).get().getContents(),HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
}
