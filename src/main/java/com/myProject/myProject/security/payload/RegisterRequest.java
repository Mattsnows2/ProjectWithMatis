package com.myProject.myProject.security.payload;

import java.util.Set;

public class RegisterRequest {

    private String username;
    private String password;
    private Set<String> role;

    public RegisterRequest(String username, String password) {

        this.username = username;
        this.password = password;

 
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
        
}
