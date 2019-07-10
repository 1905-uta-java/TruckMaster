package com.revature.project02.models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.stereotype.Component;

@Component
@Entity
@PrimaryKeyJoinColumn(name="USER_ID")
public class Admin extends User {
	public Admin() {
		super();
		userType = "ADMIN";
	}
}