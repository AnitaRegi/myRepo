package com.oracle.users.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.users.model.entity.Role;
import com.oracle.users.repo.RoleRepository;
import com.oracle.users.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service(value = "roleService")
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public Role findByName(String name) {
    	
		log.debug("Inside RoleServiceImpl.findByName() entered..." );
        Optional<Role> role = roleRepo.findRoleByName(name);
		log.debug("Inside RoleServiceImpl.findByName() exited..." );

        return role.get();
    }
}