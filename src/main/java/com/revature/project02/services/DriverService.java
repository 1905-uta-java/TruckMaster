package com.revature.project02.services;

import java.util.List;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.models.User;

public interface DriverService {
	
	public List<Driver> getAllDrivers();
	public Driver getDriverById(Integer id);
	public Driver addDriver(Driver d);
	public Driver updateDriver(Driver d);
	public boolean deleteDriver(Driver d);
	public List<Manager> getManagers();
	public Manager getManager();
	public List<Route> getRoutes();
	public Route getRoute(Integer routeId);
	
}