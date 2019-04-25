package ua.od.atomspace.rocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.od.atomspace.rocket.domain.Course;
import ua.od.atomspace.rocket.domain.Level;
import ua.od.atomspace.rocket.repository.LevelRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class LevelService {
    private final LevelRepository levelRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Transactional
    public void deleteAndReplace(Course course, int numberOfLevel) {
        Long deleteId = null;
        for (Level lvl : course.getLevels()) {
            if (levelRepository.findById(lvl.getId()).isPresent()) {
                if(lvl.getNumberOfLevel() == numberOfLevel) {
                    deleteId = lvl.getId();
                }
                else if (numberOfLevel < lvl.getNumberOfLevel()) {
                    Level level = entityManager.find(Level.class,lvl.getId());
                    level.setNumberOfLevel(level.getNumberOfLevel()-1);
                    entityManager.persist(level);
                }
            }
        }
        Level level = entityManager.find(Level.class, deleteId);
        course.getLevels().remove(level);
        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();
    }
}
