package com.oracle.users.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.users.model.entity.Role;
import com.oracle.users.repo.RoleRepository;
import com.oracle.users.service.RoleService;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public Role findByName(String name) {
        Optional<Role> role = roleRepo.findRoleByName(name);
        return role.get();
    }
}