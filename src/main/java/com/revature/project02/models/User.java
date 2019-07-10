package com.revature.project02.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USERS")
@JsonIgnoreProperties({"passHash"})
public class User {
	
	//class attribute
	@Transient
	protected String userType;
	
	public String getUserType() {
		return userType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
	@SequenceGenerator(name = "userSequence", sequenceName = "SQ_USER_PK")
	@Column(name = "USER_ID")
	private Integer id;
	
	@Column(name = "USERNAME", unique = true)
	private String username;
	
	@Column(name = "PASS_HASH")
	private String passHash;
	
	@Column(name = "EMAIL", unique = true)
	private String email;
	
	@Column(name = "PHONE", unique = true)
	private String phone;
	
	public User() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassHash() {
		return passHash;
	}
	
	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
}