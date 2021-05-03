package com.oracle.users.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.users.auth.TokenProvider;
import com.oracle.users.exception.UserServiceException;
import com.oracle.users.model.AuthToken;
import com.oracle.users.model.LoginUser;
import com.oracle.users.model.UserDto;
import com.oracle.users.model.entity.User;
import com.oracle.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser)   {
        log.info("Initiating login for: {}", loginUser.getUsername());
        String token=null;
        try {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        System.out.println("hi14 Authentication : "+ authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token= jwtTokenUtil.generateToken(authentication);
        System.out.println("hi17");

        }catch(AuthenticationException ex) {
        	log.error("Authentication Error!! Invalid credentials!");
        	throw new UserServiceException("Authentication Error",ex.getMessage(),HttpStatus.UNAUTHORIZED);
        	
        }
        
        log.info("User successfully Loggedin!!");
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody UserDto user) throws Exception{
    	
        log.info("Registering user {}...", user.getUsername());
        return userService.save(user);
    }
    
    @PostMapping("/guest-login")
    public ResponseEntity<?> loadGuestUser() throws Exception{
        log.info("Generating token for guest user ..." );

    	LoginUser loginUser = new LoginUser();
    	loginUser.setUsername("Guest");
    	loginUser.setPassword("GUEST");
    	
    	return generateToken(loginUser);
    }
        	
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers(){
        log.info("Retrieving all users ...");
        return userService.findAll();
    }
    
    @GetMapping("/{username}")
    public User getUserByUserName( @PathVariable("username") String username){
    	
        log.info("Retrieving all users ...");
        return userService.findOne(username);
    }

}
