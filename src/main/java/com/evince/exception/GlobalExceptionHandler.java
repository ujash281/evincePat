package com.evince.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.evince.payloads.ApiResponse;





//it will check the exception on the controller level
@RestControllerAdvice
public class GlobalExceptionHandler {

	//it will handle ResourseNotFoundException type exception raised form the controller
	@ExceptionHandler(ResourseNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotfoundExceptionHandler(ResourseNotFoundException ex){
		
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	//it will handle MethodArgumentNotValidException type exception raise form the controller
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		Map<String,String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		}));
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	//it will handle MethodArgumentNotValidException type exception raise form the api controller
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiExcceptionHandler(ApiException ex){
		
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
}
