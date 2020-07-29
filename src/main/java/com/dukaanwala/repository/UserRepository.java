package com.dukaanwala.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dukaanwala.model.User;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	
	List<User> findByIdNotIn(List<Integer> userIds, Pageable pageable);

	
	List<User> findByfirstNameContaining(String matchString, Pageable pageable);
	
	@Query("from User u where u.firstName like :firstName%")
	List<User> getAllUserByFirstName(String firstName);
	
	
		
	@Query("from User u order by u.id desc")
	Page<User> getAllUser(Pageable pageable);

	
	@Query("from User u where u.email like :email and u.firstName like :firstName and u.lastName like :lastName order by u.id desc")
	Page<User> getUserByEmailAndFirstNameLastName(String email, String firstName, String lastName,Pageable pageable);


	@Query("from User u where u.firstName like :searchParam1 or u.firstName like :searchParam2 or u.lastName like :searchParam1 or u.lastName like :searchParam2 or u.email like :searchParam1 order by u.id desc")
	Page<User> searchUserByEmailOrFirstNameOrLastName(String searchParam1, String searchParam2, Pageable pageable);

}
