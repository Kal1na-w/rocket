package ua.od.atomspace.sarafan.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity

public class UserInCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.PERSIST})
    private User user;

    @ManyToOne(targetEntity = Course.class,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Course course;

    @Enumerated(EnumType.STRING)
    private RoleInCourse roleInCourse;

    private int progress;

    public UserInCourse() {
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public RoleInCourse getRoleInCourse() {
        return roleInCourse;
    }

    public void setRoleInCourse(RoleInCourse roleInCourse) {
        this.roleInCourse = roleInCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInCourse)) return false;
        UserInCourse that = (UserInCourse) o;
        return getId().equals(that.getId()) &&
                getRoleInCourse() == that.getRoleInCourse();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleInCourse());
    }
}
