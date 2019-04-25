package ua.od.atomspace.rocket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Content {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(targetEntity = Level.class)
    @JsonIgnore
    private Level level;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "contents",targetEntity = UserInCourse.class)
    @JsonIgnore
    private List<UserInCourse> userInCourse;

    @NotNull
    private String context;

    public Content() {
    }

    public Long getId() {
        return id;
    }

    public Level getLevel() {
        return level;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserInCourse> getUserInCourse() { return userInCourse; }

    public void setUserInCourse(List<UserInCourse> userInCourse) {
        this.userInCourse = userInCourse;
    }

    public void setLevel(Level level) {
        this.level = level;
    }


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
