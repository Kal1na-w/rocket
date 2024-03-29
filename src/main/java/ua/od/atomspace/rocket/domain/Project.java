package ua.od.atomspace.rocket.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    private String github;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @OneToMany(targetEntity = UserInProject.class,cascade = CascadeType.ALL,mappedBy = "project",orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<UserInProject> users;

    public Project() {
    }

    private Long getId() {
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

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Set<UserInProject> getUsers() {
        return users;
    }

    public void setUsers(Set<UserInProject> users) {
        this.users = users;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projectStatus=" + projectStatus +
                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(getId(), project.getId()) &&
                Objects.equals(getName(), project.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
