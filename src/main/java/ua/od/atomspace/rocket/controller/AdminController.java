package ua.od.atomspace.rocket.controller;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mortbay.jetty.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.od.atomspace.rocket.payload.AddRoleRequest;

@RestController
@RolesAllowed("ROLE_ADMIN")
@RequestMapping("/api/admin/")
public class AdminController {

    @PersistenceContext
    private EntityManager entityManager;

    // @Transactional
    // @PostMapping("/addRole")
    // public ResponseEntity<?> addRole(@RequestBody AddRoleRequest addRoleRequest) {
        
    //     return new ResponseEntity<>(HttpStatus.Bad_Request);
    // }
}