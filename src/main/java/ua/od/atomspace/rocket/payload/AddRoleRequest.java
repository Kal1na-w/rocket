package ua.od.atomspace.rocket.payload;

public class AddRoleRequest {
    private String username;
    private String role;

    public AddRoleRequest(String username,String role) {
        this.username = username;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

}