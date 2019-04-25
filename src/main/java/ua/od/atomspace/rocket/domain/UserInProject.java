package ua.od.atomspace.rocket.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class UserInProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class,cascade = {CascadeType.PERSIST})
    private User user;

    @ManyToOne(targetEntity = Project.class,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Project project;

    @Enumerated(EnumType.STRING)
    private RoleInProject roleInProject;

    @Enumerated(EnumType.STRING)
    private ProjectSubTeam projectSubTeam;

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

    public ProjectSubTeam getProjectSubTeam() {
        return projectSubTeam;
    }

    public void setProjectSubTeam(ProjectSubTeam projectSubTeam) {
        this.projectSubTeam = projectSubTeam;
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
