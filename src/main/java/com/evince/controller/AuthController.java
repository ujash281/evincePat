package com.evince.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evince.entity.User;
import com.evince.exception.ApiException;
import com.evince.payloads.JwtAuthRequest;
import com.evince.payloads.JwtAuthResponse;
import com.evince.repo.UserRepo;
import com.evince.security.JwtTokenHelper;
import com.evince.service.UserService;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
		@RequestBody JwtAuthRequest request	) throws Exception {
		System.out.println("++0"+request.getUsername());
		this.authenticate(request.getUsername() , request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		System.out.println("dddd"+userDetails);
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response , HttpStatus.OK); 
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
	
			try {
				this.authenticationManager.authenticate(authenticationToken);
			} catch (BadCredentialsException e) {
				
				System.out.println("Invalid Username or password");
				throw new ApiException("Invalid Username or password");
			}
			
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		 User email = this.userRepo.findByEmail(user.getEmail());
		   if(email!=null) {
			   return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"error\":\"User already exitst\"}");
			
		   }else {
			
			   User registeredUser = this.userService.saveUser(user);
				return new ResponseEntity<User>(registeredUser , HttpStatus.CREATED);
		}
		    
		
	}
 	
}
