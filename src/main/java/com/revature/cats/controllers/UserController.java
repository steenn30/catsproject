package com.revature.cats.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.cats.dtos.Credentials;
import com.revature.cats.models.User;
import com.revature.cats.services.UserService;

//We cacn specify the base url of /users here, then we don't need it on individual methods
// RequestMapping matches all methods
@RequestMapping("/users")
@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAll();
	}
	@PostMapping
	public User createNewUser(@RequestBody User user, HttpSession session) {
		if(session.getAttribute("isLoggedIn") != null && (Boolean) session.getAttribute("isLoggedIn")) {
			return userService.create(user);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Integer id) {
		return userService.getById(id);
	}
	
	@PostMapping("/{id}")
	public User createNewUser(@RequestBody User user) {
		return userService.create(user);
	}
	
	@PutMapping("/{id}")
	public User createOrUpdateUserWithId(@RequestBody User user, @PathVariable Integer id) {
		user.setUserId(id);
		return userService.createOrUpdate(user);
		
	}
//	@PostMapping("/login")
//	public Boolean attemptLogin(@RequestBody Credentials creds, HttpSession session) {
//		Boolean isLoggedIn =  userService.checkCredentials(creds.getUsername(), creds.getPassword());
//		
//		session.setAttribute("isLoggedIn", isLoggedIn);
//		
//		return isLoggedIn;
//	}
}
