package com.oracle.users.service;

import java.util.List;

import com.oracle.users.model.UserDto;
import com.oracle.users.model.entity.User;

public interface UserService {
    User save(UserDto user) throws Exception;
    List<User> findAll();
    User findOne(String username);
}
