package com.evince.service;

import java.util.List;

import com.evince.entity.User;

public interface UserService {

	List<User> getAllUsers();

	User saveUser(User user);
	
	User getUserById(Integer userId);

	void deleteById(Integer userId);

	User updateUser(User user, Integer userId);

}
