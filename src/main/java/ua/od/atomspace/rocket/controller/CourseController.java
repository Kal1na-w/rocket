package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.od.atomspace.rocket.domain.*;
import ua.od.atomspace.rocket.repository.CourseRepository;
import ua.od.atomspace.rocket.repository.LevelRepository;
import ua.od.atomspace.rocket.repository.UserInCourseRepository;
import ua.od.atomspace.rocket.security.CurrentUser;
import ua.od.atomspace.rocket.service.LevelService;

import javax.annotation.security.RolesAllowed;
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
    public ResponseEntity <Course> post(@CurrentUser User user,@RequestBody Course course) {
        if (!user.getRoles().contains(Role.MENTOR) || !user.getRoles().contains(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity <Course> put(@CurrentUser User user,@PathVariable("id") Long id, @RequestBody Course course) {
        if(!user.getRoles().contains(Role.ADMIN) || !(userInCourseRepository.findByUserAndCourse(user,course).getRoleInCourse() == RoleInCourse.LEAD)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
    public ResponseEntity <?> delete(@CurrentUser User user,@PathVariable("id") Course pathCourse) {
        if(!user.getRoles().contains(Role.ADMIN) || !(userInCourseRepository.findByUserAndCourse(user,pathCourse).getRoleInCourse() == RoleInCourse.LEAD)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Course course = entityManager.find(Course.class,pathCourse.getId());
        entityManager.remove(course);
        entityManager.flush();
        entityManager.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Level endpoints
     */


    @Transactional
    @PostMapping("/{courseId}/levels")
    public ResponseEntity<Level> postLevelToCourse(@CurrentUser User user,@PathVariable("courseId") Course pathCourse,@RequestBody Level level) {
        if(!user.getRoles().contains(Role.ADMIN) || !(userInCourseRepository.findByUserAndCourse(user,pathCourse).getRoleInCourse() == RoleInCourse.LEAD)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(courseRepository.findById(pathCourse.getId()).isPresent()) {
            level.setNumberOfLevel(levelRepository.findAllByCourseOrderByNumberOfLevelAsc(courseRepository.findById(pathCourse.getId()).get()).size()+1);
            level.setCourse(courseRepository.findById(pathCourse.getId()).get());
            Course course = entityManager.find(Course.class,pathCourse.getId());
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
    public ResponseEntity<Level> getLevelByCourse(@CurrentUser User user,@PathVariable("courseId") Course pathCourse,@PathVariable int numberOfLevel) {
        if(!(userInCourseRepository.findByUserAndCourse(user,pathCourse).getProgress() >= numberOfLevel)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(courseRepository.findById(pathCourse.getId()).isPresent()) {
            return new ResponseEntity<>(levelRepository.findByCourseAndNumberOfLevel(courseRepository.findById(pathCourse.getId()).get(),numberOfLevel),HttpStatus.OK);
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
    public ResponseEntity<Level> deleteLevelByCourse(@CurrentUser User user,@PathVariable("courseId") Course pathCourse,@PathVariable int numberOfLevel) {
        if(!user.getRoles().contains(Role.ADMIN) || !(userInCourseRepository.findByUserAndCourse(user,pathCourse).getRoleInCourse() == RoleInCourse.LEAD)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(courseRepository.findById(pathCourse.getId()).isPresent()) {
            levelService.deleteAndReplace(courseRepository.findById(pathCourse.getId()).get(),numberOfLevel);
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
    public ResponseEntity<Content> postContentByLvl(@CurrentUser User user,@PathVariable("courseId") Course pathCourse, @PathVariable Long levelId, @RequestBody Content content){
        if(!user.getRoles().contains(Role.ADMIN) || !(userInCourseRepository.findByUserAndCourse(user,pathCourse).getRoleInCourse() == RoleInCourse.LEAD)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(courseRepository.findById(pathCourse.getId()).isPresent()) {
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
    public ResponseEntity<?> deleteContentByLvl(@CurrentUser User user,@PathVariable("courseId") Course pathCourse, @PathVariable Long levelId, @PathVariable Long contentId){
        if(!user.getRoles().contains(Role.ADMIN) || !(userInCourseRepository.findByUserAndCourse(user,pathCourse).getRoleInCourse() == RoleInCourse.LEAD)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(courseRepository.findById(pathCourse.getId()).isPresent()) {
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
