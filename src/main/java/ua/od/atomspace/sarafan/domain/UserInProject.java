package ua.od.atomspace.sarafan.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class UserInProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.PERSIST})
    private User user;

    @ManyToOne(targetEntity = Project.class,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Project project;

    @Enumerated(EnumType.STRING)
    private RoleInProject roleInProject;

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public RoleInProject getRoleInProject() {
        return roleInProject;
    }

    public void setRoleInProject(RoleInProject roleInProject) {
        this.roleInProject = roleInProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInProject)) return false;
        UserInProject that = (UserInProject) o;
        return Objects.equals(getId(), that.getId()) &&
                getRoleInProject() == that.getRoleInProject();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleInProject());
    }
}
