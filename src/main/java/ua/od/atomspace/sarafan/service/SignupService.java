package ua.od.atomspace.sarafan.service;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.od.atomspace.sarafan.domain.Role;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.repository.UserRepository;


@Service
@Transactional
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     *
     * set up a default user with two roles USER and ADMIN
     *
     */
    private void setupDefaultUser() {
        //-- just to make sure there is an ADMIN user exist in the database for testing purpose
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("crmadmin");
            user.setPassword(passwordEncoder.encode("adminpass"));
            user.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(user);
        }
    }


}
