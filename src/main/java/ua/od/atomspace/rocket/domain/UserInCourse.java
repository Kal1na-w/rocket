package ua.od.atomspace.rocket.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity

public class UserInCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_in_course_id")
    private Long id;

    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.PERSIST})
    private User user;

    @ManyToOne(targetEntity = Course.class,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Course course;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinTable(
            name = "user_in_course_content",
            joinColumns = {@JoinColumn(name = "user_in_course_id")},
            inverseJoinColumns = {@JoinColumn(name = "content_id")}
    )
    private List<Content> contents;

    @Enumerated(EnumType.STRING)
    private RoleInCourse roleInCourse;

    @NotNull
    private int progress;

    public UserInCourse() {
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
