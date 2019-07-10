package com.revature.project02.models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@PrimaryKeyJoinColumn(name="USER_ID")
@JsonIgnoreProperties({"passHash"})
public class Admin extends User {
	public Admin() {
		super();
		userType = "ADMIN";
	}
}