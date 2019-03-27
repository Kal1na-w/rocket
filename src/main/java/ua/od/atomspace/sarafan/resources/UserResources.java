package ua.od.atomspace.sarafan.resources;

import org.springframework.hateoas.ResourceSupport;
import ua.od.atomspace.sarafan.domain.Role;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.domain.UserInCourse;
import ua.od.atomspace.sarafan.domain.UserInProject;

import java.time.LocalDateTime;
import java.util.Set;

public class UserResources extends ResourceSupport {
    private Set<Role> roles;
    private String username;
    private String email;
    private String telegram;
    private LocalDateTime lastVisit;
    private Set<UserInCourse> courses;
    private Set<UserInProject> projects;

    public UserResources(User user) {
        this.username = user.getUsername();
        this.lastVisit = user.getLastVisit();
        this.roles = user.getRoles();
        this.email = user.getEmail();
        this.telegram = user.getTelegram();
        this.courses = user.getCourses();
        this.projects = user.getProjects();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTelegram() {
        return telegram;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public Set<UserInCourse> getCourses() {
        return courses;
    }

    public Set<UserInProject> getProjects() {
        return projects;
    }
}
