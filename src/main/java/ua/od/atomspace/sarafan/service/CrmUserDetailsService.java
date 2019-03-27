package ua.od.atomspace.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class CrmUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if(user == null){
            throw new UsernameNotFoundException("UserName "+userName+" not found");
        }
        user.setLastVisit(LocalDateTime.now());
        return user;
    }

}
