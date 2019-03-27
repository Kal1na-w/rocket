package ua.od.atomspace.sarafan.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.od.atomspace.sarafan.domain.User;


@RestController
public class UserController {

    @RequestMapping(value = "/api/users/currentUser", method = RequestMethod.GET,produces = "application/json")
    public User currentUser(@AuthenticationPrincipal User user) {
        return user;
    }
}

