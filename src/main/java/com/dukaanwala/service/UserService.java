package com.dukaanwala.service;

import java.util.List;

import com.dukaanwala.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

   User findByUserEmail(String email);

   User findByUserName(String userName);

   User saveUser(User user);

   User findByUserById(int userId);



   User getUserById(int userId);

   List<User> getAllMatchingFirstName(String matchString, Pageable pageable);
   
   List<User> getAllUserByFirstName(String firstName);

   Page<User> getAllUser(Pageable pageable);

   Page<User> getUserByEmailAndFirstNameLastName(String email, String firstName, String lastName,Pageable pageable);

   Page<User> searchUserByEmailOrFirstNameOrLastName(String searchParam1, String searchParam2, Pageable pageable);

   
}
