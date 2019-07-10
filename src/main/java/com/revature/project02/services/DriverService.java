package com.revature.project02.services;

import java.util.List;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.models.User;

public interface DriverService {
	
	public List<Driver> getAllDrivers();
	public List<Driver> getDriversByManager(Manager manager);
	public Driver getDriverById(Integer id);
	public Driver getDriverByUsername(String username);
	public Driver addDriver(Driver d, Manager manager, String password);
	public Driver addDriver(Driver d);
	public Driver updateDriver(Driver driver);
	public boolean mutchDriver(Driver d);
	public boolean mutchDriver(Integer id);
	
}