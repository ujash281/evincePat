package com.evince.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evince.entity.User;
import com.evince.service.UserService;



@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers,HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
		User userById = this.userService.getUserById(userId);
		String string = userById.getDob().toString();
		System.out.println(string.substring(0,4));
		return new ResponseEntity<User>(userById,HttpStatus.OK);
	}
	
	@GetMapping("/deleteUser/{userId}")
	public ResponseEntity<String> deleteById(@PathVariable Integer userId) {
		this.userService.deleteById(userId);
		return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
	}
	
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable Integer userId) {
		User updatedUser = this.userService.updateUser(user, userId);
		return new ResponseEntity<User>(updatedUser , HttpStatus.OK);
	}
	
	
	

}
