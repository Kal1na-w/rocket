package ua.od.atomspace.rocket.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    @OneToMany(targetEntity = UserInCourse.class, cascade = CascadeType.ALL, mappedBy = "course", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserInCourse> users;

    @NotNull
    private byte priority;

    @OneToMany(targetEntity = Level.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "course", orphanRemoval = true)
    private List<Level> levels;

    public Course() {
    }

    public Course(String name, byte priority) {
        this.name = name;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserInCourse> getUsers() {
        return users;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public void setUsers(Set<UserInCourse> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name='" + name + '\'' + ", users=" + users + ", priority=" + priority
                + ", levels=" + levels + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Course))
            return false;
        Course course = (Course) o;
        return getId().equals(course.getId()) && getName().equals(course.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
