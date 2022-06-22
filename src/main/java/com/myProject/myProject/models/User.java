package com.myProject.myProject.models;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "users",
     uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    

    private String email;

    private String password;

@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Roles> roles =new HashSet<>();

    public User(){

    }

    public User (String email, String password) {
      
      
        this.email = email;
        this.password = password;
}

public long getId() {
    return id;

}

public void setId(long id) {
     this.id = id;

}

public String getEmail() {
    return email;
}

public void setEmail(String email) {

    this.email = email;
}



public String getPassword() {
    return password;
}
 public void setPassword(String password){
    this.password = password;
 }

 

 public Set<Roles> getRoles() {
    return roles;
}

public void setRoles(Set<Roles> roles) {
    this.roles = roles;
}

}


