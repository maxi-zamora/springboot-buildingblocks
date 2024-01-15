package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	// getAll Users
	@GetMapping
	public CollectionModel<User> getAllUsers() throws UserNotFoundException {
		List<User> allUsers = userService.getAllUsers();
		for(User user: allUsers) {
			//Self Link
			Long userId = user.getId();
			Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			user.add(selfLink);
			
			//Relationship link with getAllOrders
			CollectionModel<Order> orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class)
					.getAllOrders(userId);
			Link ordersLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
			user.add(ordersLink);
		}
		
		//Self link for getAllUsers
		Link selfLinkgetAllUsers =  WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
		return CollectionModel.of(allUsers, selfLinkgetAllUsers);
	}

	// getUserById
	@GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user  = userOptional.get();
			Long userid = user.getId();
			Link selflink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selflink);
			return EntityModel.of(user);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
}