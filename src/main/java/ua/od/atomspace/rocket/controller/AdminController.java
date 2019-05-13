package ua.od.atomspace.rocket.controller;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ua.od.atomspace.rocket.domain.Role;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.payload.ChangeRoleRequest;
import ua.od.atomspace.rocket.repository.UserRepository;
import ua.od.atomspace.rocket.security.CurrentUser;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/changeRole")
    public ResponseEntity<User> addRole(@CurrentUser User currentUser, @RequestBody ChangeRoleRequest changeRoleRequest) {
        if (!currentUser.getRoles().contains(Role.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(userRepository.findByUsername(changeRoleRequest.getUsername())!=null) {
            User user = entityManager.find(User.class,userRepository.findByUsername(changeRoleRequest.getUsername()).getId());
            user.setRoles(changeRoleRequest.getRoles());
            entityManager.persist(user);
            entityManager.flush();
            entityManager.clear();
            return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}