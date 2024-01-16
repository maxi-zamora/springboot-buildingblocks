package com.stacksimplify.restservices.entities;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

//Entity
@Entity
@Table(name= "CUSTOM_USER")
// @JsonIgnoreProperties({"firstname", "lastname"}) - Static Filtering JsonIgnore
// @JsonFilter(value="userFilter") -- Used for MappingJackson value filtering section
public class User extends RepresentationModel {
	
	@Id
	@GeneratedValue
	@JsonView(Views.External.class)
	private Long id;
	
	@NotEmpty(message="Username is a Mandatory field. Please provide an username")
	@Column(name = "USER_NAME", length = 50, nullable=false, unique=true)
	@JsonView(Views.External.class)
	private String username;
	
	@Size(min=2, message="FirstName should have at least 2 characters")
	@Column(name = "FIRST_NAME", length = 50, nullable=false)
	@JsonView(Views.External.class)
	private String firstname;
	
	@Column(name = "LAST_NAME", length = 50, nullable=false)
	@JsonView(Views.External.class)
	private String lastname;
	
	@Column(name = "EMAIL", length = 50, nullable=false)
	@JsonView(Views.External.class)
	private String email;
	
	@Column(name = "ROLE", length = 50, nullable=false)
	@JsonView(Views.Internal.class)
	private String role;
	
	@Column(name = "SSN", length=50, nullable=false, unique=true)
	// @JsonIgnore -- Static Filtering Json Ignore
	@JsonView(Views.Internal.class)
	private String ssn;
	
	@OneToMany(mappedBy = "user")
	@JsonView(Views.Internal.class)
	private List<Order> order;
	
	@Column(name = "ADDRESS")
	private String address;
	
	//No Argument Constructor
	public User() {
	}
	
	//Fields Constructor
	public User(Long id, String username, String firstname, String lastname, String email, String role, String ssn,  String address) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.role = role;
		this.ssn = ssn;
		this.address = address;
	}

	//Getters and Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", email=" + email + ", role=" + role + ", ssn=" + ssn + ", order=" + order + ", address=" + address
				+ "]";
	}
}
