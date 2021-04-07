package com.oracle.users.service;

import com.oracle.users.model.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
