package com.revature.project02.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;

/**
 * Description - Interface for Driver table in db
 * @author mattd
 * @version 07/09/2019
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
	
	/**
	 * Description - Returns a list of drivers by their ID
	 * @param id - Integer representation of a driver's id
	 * @return - List of drivers
	 * @throws - nothing.
	 */
	public List<Driver> getAllDriversById(Integer id);
	
	/**
	 * Description - Returns a list of drivers under the specified manager
	 * @param manager - Manager object that manages the queried list of drivers
	 * @return - a List of drivers
	 * @throws - nothing.
	 */
	public List<Driver> getDriversByManager(Manager manager);
	
	/**
	 * Description - Returns a driver with the specified username
	 * @param username - String representation of the driver's username
	 * @return - a Driver with the specified paramter of username
	 * @throws - NullPointerException npe - There is no such driver.
	 */
	public Driver getDriverByUsername(String username);
	
}