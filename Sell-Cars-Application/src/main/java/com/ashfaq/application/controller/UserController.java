package com.ashfaq.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashfaq.application.model.ApplicationUser;
import com.ashfaq.application.service.ApplicationUserService;

@RestController
@RequestMapping("/admin/users")
public class UserController {
	
	
	  @Autowired
    private  ApplicationUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //ADMIN CAN CREATE USER with any roles , but is there to create admin accounts
    @PostMapping
    public ResponseEntity<ApplicationUser> createUser(@RequestBody ApplicationUser user) {
    	
    	 user.setPassword(passwordEncoder.encode(user.getPassword()));
    	
        ApplicationUser savedUser = userService.saveApplicationUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationUser> updateUser(@PathVariable Long id, @RequestBody ApplicationUser ApplicationUser) {
        ApplicationUser updatedUser = userService.updateApplicationUser(id, ApplicationUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUser> getUserById(@PathVariable Long id) {
        ApplicationUser ApplicationUser = userService.getApplicationUserById(id);
        return new ResponseEntity<>(ApplicationUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationUser>> getAllUsers() {
        List<ApplicationUser> users = userService.getAllApplicationUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteApplicationUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<ApplicationUser> findByUsername(@RequestParam String username) {
        ApplicationUser ApplicationUser = userService.findByApplicationUsername(username);
        return new ResponseEntity<>(ApplicationUser, HttpStatus.OK);
    }
}
