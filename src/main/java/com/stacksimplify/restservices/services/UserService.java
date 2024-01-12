package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;

//Service
@Service
public class UserService {
	
	//Autowire the UserRepository
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	//CreateUser Method
	public User createUser(User user) throws UserExistsException {
		//if user exist using username
		//If user doesn't exists throw UserExistsException
		User existingUser = userRepository.findByUsername(user.getUsername());
		if(existingUser != null)
			throw new UserExistsException("User already exists in the repository");
		else
			return userRepository.save(user);
	}
	
	//getUserById
	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent())
			return user;
		else
			throw new UserNotFoundException("User not found in user repository");
	}
	
	//updateUserById
	public User updateUserById(Long id, User user) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			user.setId(id);
			return userRepository.save(user);
		} else {
			throw new UserNotFoundException("User not found in user repository, please, provide the correct user id");
		}
		
	}
	
	//deleteUserById
	public void deleteUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			userRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found in user repository, please, provide the correct user id");
		}
	}
	
	
	//getUserByUsername
	public User getUserByUsername( String username) {
		return userRepository.findByUsername(username);
	}
}
