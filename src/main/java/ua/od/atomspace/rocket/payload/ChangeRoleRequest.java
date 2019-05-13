package ua.od.atomspace.rocket.payload;

import ua.od.atomspace.rocket.domain.Role;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class ChangeRoleRequest {
    @NotNull
    private String username;
    @NotNull
    private Set<Role> roles;

    public ChangeRoleRequest(String username, Set<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}