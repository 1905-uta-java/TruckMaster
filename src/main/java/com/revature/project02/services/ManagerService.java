package com.revature.project02.services;
//imports
import java.util.List;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Route;
/**
 * Description - Interface for Managers methods
 * @author mattd
 * @version 07/09/2019
 */
public interface ManagerService {
	public List<Driver> getAllDrivers();
	public Driver getDriverByID(Integer id);
	public Driver addDriver(Driver d);
	public Driver fireDriver(Driver d);
	public int assignRouteToDriver(Driver d, Route r);
}
