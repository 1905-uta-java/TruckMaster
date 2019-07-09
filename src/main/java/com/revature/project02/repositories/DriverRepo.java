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
public interface DriverRepo extends JpaRepository<Driver, Integer> {
	
	/**
	 * Description - Returns a list of drivers by their ID
	 * @param id - Integer representation of a driver's id
	 * @return - List of drivers
	 * @throws - nothing.
	 */
	public List<Driver> getAllDriversById(Integer id);
	public Driver getDriverByManager(Manager manager);
	
}