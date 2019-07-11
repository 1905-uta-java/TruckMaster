package com.revature.project02.controllers;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//imports
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.services.DriverService;
import com.revature.project02.services.ManagerService;
import com.revature.project02.services.RouteService;

/**
 * Description - Controller for driver requests
 * @author mattD, joshuaV, wolfeM
 * @version 07/09/2019
 */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/driver")
public class DriverController {//class header
	//class attributes
	@Autowired
	private DriverService dService;
	@Autowired
	private ManagerService mService;
	@Autowired
	private RouteService rService;
	
	
	/**
	 * Description - Adds a driver to is under the Manager that added the driver
	 * @param id - The manager's id
	 * @param driver - The driver information in json
	 * @param password - String of unencrypted password for the driver
	 * @return - json representation of the newly created driver and http status created, else failed.
	 * @throws BadRequestException - if manager is not found or exists, or if driver could not be read.
	 */
	@PostMapping(value= "/add-driver-managerid-{id}")
	public ResponseEntity<Driver> addDriver(@PathVariable("id") Integer id, @RequestParam("driver") String driverStr, 
			@RequestParam("password")String password) {
		ObjectMapper om = new ObjectMapper();
		Driver driver;
		try {
			driver = om.readValue(driverStr, Driver.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BadRequestException("Driver could not be instantiated. Check Json.");
		}
		Manager temp = mService.getManagerById(id);
		return new ResponseEntity<>(dService.addDriver(driver, temp, password), HttpStatus.CREATED);
	}
	
	/**
	 * Description - Gets all drivers under this manager
	 * @param id - the Integer representation of the manager's id
	 * @return - a List of drivers under this manager
	 * @throws ResourceNotFoundException - if there are no drivers under this manager
	 * @throws BadRequestException - if the manager doesn't exist, well, at least that specific id doesn't exist
	 */
	@GetMapping(value = "/get-all-drivers-managerid-{id}")
	public ResponseEntity<List<Driver>> getAllDrivers(@PathVariable("id") Integer id){
		return new ResponseEntity<>(dService.getDriversByManager(mService.getManagerById(id)), HttpStatus.OK);
	}
	
	/**
	 * Description - Gets all drivers
	 * @return - a List of all drivers within the driver table
	 * @throws - ResourceNotFoundException - if there are no drivers
	 */
	@GetMapping
	public ResponseEntity<List<Driver>> getAllDrivers(){
		return new ResponseEntity<>(dService.getAllDrivers(), HttpStatus.OK);
	}
	
	/**
	 * Description - Deletes the requested driver
	 * @param id - Integer representation of the driver's id
	 * @return - HttpStatus of OK
	 * @throws - BadRequestException - if the driver id doesn't exist
	 */
	@DeleteMapping(value = "/driverid-{id}")
	public HttpStatus deleteDriver(@PathVariable("id") Integer id) {
		dService.mutchDriver(id);
		return HttpStatus.OK;
	}
	
	
}//end of class
