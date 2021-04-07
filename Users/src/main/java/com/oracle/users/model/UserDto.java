package com.oracle.users.model;

import com.oracle.users.model.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private Boolean isAdmin;


    public User getUserFromDto(){
        User user = new User(username,password,email,phone,name);
        return user;
    } 
}
