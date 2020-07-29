package com.dukaanwala.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import com.dukaanwala.dto.requestdto.LoginRequestDto;
import com.dukaanwala.dto.requestdto.SaveUserRequestDto;
import com.dukaanwala.dto.responsedto.AppResponse;
import com.dukaanwala.exceptions.AppErrorResponse;
import com.dukaanwala.exceptions.ConflictException;
import com.dukaanwala.exceptions.NotExistsException;
import com.dukaanwala.model.User;
import com.dukaanwala.service.UserService;
import com.dukaanwala.service.UtilService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Authentication
 */
@RestController
@RequestMapping("/auth")
public class Authentication {
	private static final Logger LOGGER = LoggerFactory.getLogger(Authentication.class);

	@Autowired
	UtilService utilService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;



	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppResponse> login(@Valid @RequestBody LoginRequestDto loginRequestDto,
			BindingResult bindingResult) {

		AppResponse appResponse = new AppResponse();
		if (bindingResult.hasErrors()) {
			AppErrorResponse configErrorResponse = new AppErrorResponse();
			List<AppErrorResponse.Error> errors = new ArrayList<AppErrorResponse.Error>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				AppErrorResponse.Error rError = new AppErrorResponse.Error();
				rError.setCode(HttpStatus.BAD_REQUEST.value());
				rError.setUserMessage(fieldError.getField());
				rError.setInternalMessage(fieldError.getDefaultMessage());
				errors.add(rError);
				LOGGER.error(bindingResult.getFieldError().getDefaultMessage());
			}
			configErrorResponse.setErrors(errors);
			appResponse.setResponse(configErrorResponse);
			return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
		}
		User user = userService.findByUserEmail(loginRequestDto.getEmail());
		if (user == null) {
			throw new NotExistsException("User not exists.");
		}
		else {
			if (!utilService.autheticatePassword(user.getPassword(), loginRequestDto.getPassword())) {
				appResponse.setResponse(null);
				appResponse.setDescription("Password  not matched.");
				return new ResponseEntity<>(appResponse, HttpStatus.UNAUTHORIZED);
			}
			
		}
		user.setDeviceToken(loginRequestDto.getDeviceToken());
		user = userService.saveUser(user);
		user.setDeviceToken(null);
		user.setCreatedOn(null);
		user.setPassword(null);
		appResponse.setDescription("Login successfully.");
		appResponse.setResponse(user);
		return new ResponseEntity<>(appResponse, HttpStatus.CREATED);

	}


	@GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppResponse> logOut(@Valid @Email @RequestParam("email") String email) throws Exception {
		AppResponse appResponse = new AppResponse();
		User user = userService.findByUserEmail(email);
		if (user == null) {
			throw new NotExistsException("No user with this email address.");
		}
		user.setDeviceToken(null);
		userService.saveUser(user);
		appResponse.setDescription("Logout successfully.");
		appResponse.setResponse(null);
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/saveUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppResponse> saveUser(@Valid @RequestBody SaveUserRequestDto saveUserRequestDto,
			BindingResult bindingResult) throws Exception {
		AppResponse appResponse = new AppResponse();
		if (bindingResult.hasErrors()) {
			AppErrorResponse configErrorResponse = new AppErrorResponse();
			List<AppErrorResponse.Error> errors = new ArrayList<AppErrorResponse.Error>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				AppErrorResponse.Error rError = new AppErrorResponse.Error();
				rError.setCode(HttpStatus.BAD_REQUEST.value());
				rError.setUserMessage(fieldError.getField());
				rError.setInternalMessage(fieldError.getDefaultMessage());
				errors.add(rError);
				LOGGER.error(bindingResult.getFieldError().getDefaultMessage());
			}
			configErrorResponse.setErrors(errors);
			appResponse.setResponse(configErrorResponse);
			return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
		}
		User user = null;
		user = userService.findByUserEmail(saveUserRequestDto.getEmail());
		if (user != null) {
			throw new ConflictException("User already there with this email.");
		}
		user = new User();
		modelMapper.map(saveUserRequestDto, user);
		user.setCreatedOn(new Date());
		user.setPassword(utilService.stringToMD5(saveUserRequestDto.getPassword()));
		user = userService.saveUser(user);
		
		
		SaveUserRequestDto saveUserResponseDTO = new SaveUserRequestDto();
		
		modelMapper.map(user, saveUserResponseDTO);
		appResponse.setDescription("User has been saved");
		appResponse.setResponse(user);
		return new ResponseEntity<>(appResponse, HttpStatus.CREATED);
	}

	//************************************************************************************//
	////end//////


}