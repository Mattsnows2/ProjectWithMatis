package com.myProject.myProject.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private String firstname;

    private String lastname;

    private String password;

    public User(){

    }

    public User (String email, String firstname, String lastname, String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
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

public String getLastName(){
    return lastname;
}

public void setLastName(String lastname) {
    this.lastname= lastname;
}

public String getPassword() {
    return password;
}
 public void setPassword(String password){
    this.password = password;
 }

 public String getFirstname() {
    return firstname;
 }

 public void setFirstname(String firstname){
    this.firstname= firstname;
 }
}


