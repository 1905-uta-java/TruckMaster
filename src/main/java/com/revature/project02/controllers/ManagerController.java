package com.revature.project02.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.services.DriverService;
import com.revature.project02.services.ManagerService;
import com.revature.project02.services.RouteService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private ManagerService mService;
	@Autowired 
	private DriverService dService;
	@Autowired
	private RouteService rService;
	
	/**
	 * Description - Create a manager object and add a record to the Manager table in db
	 * @param manager - manager object instantiated from json request body
	 * @return - HttpStatus of created and json of the created manager when success, else failed.
	 */
	@PostMapping
	public ResponseEntity<Manager> addManager(@RequestParam("manager") Manager manager, @RequestParam("password") String password) {
		return new ResponseEntity<>(mService.addManager(manager, password), HttpStatus.CREATED);
	}
	
	
	/**
	 * Description - Returns a Manager of the provided driver id
	 * @param id - Integer representation of the driver's id
	 * @return - a Manager
	 * @throws ResouceNotFoundException - If the manager does not exist or cannot be found.
	 */
	@GetMapping(value="/get-manager-driver-{id}")
	public ResponseEntity<Manager> getDriverManager(@PathVariable("id") Integer id){
		return new ResponseEntity<>(mService.getManagerByDriver(dService.getDriverById(id)), HttpStatus.FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<Manager>> getAllManagers(){
		return new ResponseEntity<>(mService.getAllManagers(), HttpStatus.FOUND);
	}
	
	
}
