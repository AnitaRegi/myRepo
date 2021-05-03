package com.oracle.users.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oracle.users.exception.UserServiceException;
import com.oracle.users.model.UserDto;
import com.oracle.users.model.entity.Role;
import com.oracle.users.model.entity.User;
import com.oracle.users.repo.UserRepository;
import com.oracle.users.service.RoleService;
import com.oracle.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("hi11");

		log.debug("Inside UserServiceImpl.loadUserByUsername() entered..." );

        Optional<User> userOpt = userRepo.findByUsername(username);
        if(!userOpt.isPresent()){
        	System.out.println("hi12");
        	log.error("Invalid username or password.");
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User user = userOpt.get();
        
		log.debug("Inside UserServiceImpl.loadUserByUsername() exited..." );
		System.out.println("hi13");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
		log.debug("Inside UserServiceImpl.getAuthority() entered..." );

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
		log.debug("Inside UserServiceImpl.getAuthority() exited..." );

        return authorities;
    }

    public List<User> findAll() {
		log.debug("Inside UserServiceImpl.findAll() entered..." );

        List<User> list = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(list::add);
		log.debug("Inside UserServiceImpl.findAll() exited..." );

        return list;
    }

    @Override
    public User findOne(String username) {
		
    	log.debug("Inside UserServiceImpl.findOne() entered..." );
        Optional<User> userOpt = userRepo.findByUsername(username);  
		log.debug("Inside UserServiceImpl.findOne() exited..." );

        return userOpt.isPresent()?userOpt.get():null;
    }

    @Override
    public User save(UserDto user) throws Exception {
    	
		log.debug("Inside UserServiceImpl.save() entered..." );

        User newUser = findOne(user.getUsername());
        if(null != newUser) {
        	log.error("Username {} Already exists!",user.getUsername());
        	throw new UserServiceException("USR001", "Username Already exists!",HttpStatus.BAD_REQUEST);
        }
        newUser = user.getUserFromDto();

        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(user.getIsAdmin()){
        	
    		log.debug("Successfully added Admin role  to user {} ... ", user.getUsername() );
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        newUser.setRoles(roleSet);
        newUser = userRepo.save(newUser);
        newUser.setPassword(null); //avoid sending password in the response.
		log.debug("Inside UserServiceImpl.save() exited..." );
        return newUser;
    }
}
