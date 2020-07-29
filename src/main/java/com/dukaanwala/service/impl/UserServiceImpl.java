package com.dukaanwala.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dukaanwala.enums.Role;
import com.dukaanwala.exceptions.NotExistsException;
import com.dukaanwala.model.User;
import com.dukaanwala.repository.UserRepository;
import com.dukaanwala.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUserEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByUserName(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}



	@Override
	public User findByUserById(int userId) {
		Optional<User> user = userRepository.findById(userId);
		return (user.isPresent() ? user.get() : null);
	}



	@Override
	public User getUserById(int userId) {

		return userRepository.getOne(userId);
	}

	
	@Override
	public List<User> getAllMatchingFirstName(String matchString, Pageable pageable) {
		if (matchString.isEmpty()) {
			return userRepository.findAll(pageable).toList();
		}
		return userRepository.findByfirstNameContaining(matchString, pageable);
	}


	@Override
	public List<User> getAllUserByFirstName(String firstName) {
		
		return userRepository.getAllUserByFirstName(firstName);
	}

	@Override
	public Page<User> getAllUser(Pageable pageable) {
		return userRepository.getAllUser(pageable);
	}

	@Override
	public Page<User> getUserByEmailAndFirstNameLastName(String email, String firstName, String lastName,
			Pageable pageable) {
				return userRepository.getUserByEmailAndFirstNameLastName(email, firstName, lastName, pageable);
	}

	@Override
	public Page<User> searchUserByEmailOrFirstNameOrLastName(String searchParam1, String searchParam2, Pageable pageable) {
				return userRepository.searchUserByEmailOrFirstNameOrLastName(searchParam1, searchParam2, pageable);
	}








}