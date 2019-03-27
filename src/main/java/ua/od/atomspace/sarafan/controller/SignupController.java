package ua.od.atomspace.sarafan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.od.atomspace.sarafan.domain.Role;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.service.SignupService;

import java.util.Collections;

@RestController
public class SignupController {

    @Autowired
    private SignupService signupService;

    /**
     *
     * user signup
     * @param user
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody User user) {
        user.setRoles(Collections.singleton(Role.USER));
        signupService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}