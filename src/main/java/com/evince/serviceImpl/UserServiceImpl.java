package com.evince.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.evince.entity.User;
import com.evince.exception.ResourseNotFoundException;
import com.evince.repo.UserRepo;
import com.evince.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwwordEncoder;
	
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(this.passwwordEncoder.encode(user.getPassword()));
		return this.userRepo.save(user);
		
	}

	@Override
	public User getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow
				(() -> new ResourseNotFoundException("User","Id",userId));
		return user;
	}

	@Override
	public void deleteById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow
				(() -> new ResourseNotFoundException("User","Id",userId));
		this.userRepo.delete(user);
	}

	@Override
	public User updateUser(User user, Integer userId) {
		User usr = this.userRepo.findById(userId).orElseThrow
				(() -> new ResourseNotFoundException("User","Id",userId));
		usr.setFirstName(user.getFirstName());
		usr.setLastName(user.getLastName());
		usr.setEmail(user.getEmail());
		usr.setPhone(user.getPhone());
		usr.setDob(user.getDob());
		return this.userRepo.save(user);
	}

	

}
