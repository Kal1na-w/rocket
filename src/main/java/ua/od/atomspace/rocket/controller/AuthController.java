package ua.od.atomspace.rocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.od.atomspace.rocket.domain.Role;
import ua.od.atomspace.rocket.domain.User;
import ua.od.atomspace.rocket.payload.ApiResponse;
import ua.od.atomspace.rocket.payload.JwtAuthenticationResponse;
import ua.od.atomspace.rocket.payload.LoginRequest;
import ua.od.atomspace.rocket.payload.SignUpAdminRequest;
import ua.od.atomspace.rocket.payload.SignUpRequest;
import ua.od.atomspace.rocket.repository.UserRepository;
import ua.od.atomspace.rocket.security.JwtTokenProvider;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @Transactional
    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody SignUpAdminRequest signUpAdminRequest) {
        if(signUpAdminRequest.getSuperSecret().equals("$2y$12$3WjFUzK8H.ZyEg1mz7z0F.gZI5/ByH5VNf97OHt1P2Ayvgoc5I6dK")) {
            User user = new User();

            user.setFirstName(signUpAdminRequest.getFirstName());

            user.setLastName(signUpAdminRequest.getLastName());

            user.setUsername(signUpAdminRequest.getUsername());

            user.setGithub(signUpAdminRequest.getGithub());

            user.setEmail(signUpAdminRequest.getEmail());

            user.setPassword(signUpAdminRequest.getPassword());

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setRoles(Collections.singleton(Role.ADMIN));

            entityManager.persist(user);
            entityManager.flush();
            entityManager.clear();

            return new ResponseEntity<>(new ApiResponse(true, "Admin registered successfully"),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User();

        user.setFirstName(signUpRequest.getFirstName());

        user.setLastName(signUpRequest.getLastName());

        user.setUsername(signUpRequest.getUsername());

        user.setGithub(signUpRequest.getGithub());

        user.setEmail(signUpRequest.getEmail());

        user.setPassword(signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(Collections.singleton(Role.USER));

        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        return new ResponseEntity<>(new ApiResponse(true, "User registered successfully"),HttpStatus.CREATED);
    }
}
