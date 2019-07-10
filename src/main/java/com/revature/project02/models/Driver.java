package com.revature.project02.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@PrimaryKeyJoinColumn(name="USER_ID")
@JsonIgnoreProperties({"manager", "routes", "passHash"})
public class Driver extends User {
	
	@ManyToOne
	@JoinColumn(name = "MANAGER_ID")
	private Manager manager;
	
	@OneToMany(mappedBy = "driver",
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	private List<Route> routes;
	
	public Driver() {
		super();
		userType = "DRIVER";
	}
	
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}
	
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
	@Override
	public String toString() {
		return "Driver [getId()=" + getId() + ", getUsername()=" + getUsername()
				+ ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone() + "]";
	}
}