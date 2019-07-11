package com.revature.project02.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.exceptions.UnauthorizedException;
import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.services.DriverService;
import com.revature.project02.services.ManagerService;
import com.revature.project02.services.RouteService;
import com.revature.project02.util.AuthTokenUtil;

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
	 * Description - Creates a new Manager object and adds a new record of the new Manager into the db table
	 * @param manager - json representation of the manager object
	 * @param password - String representation of the manager's password
	 * @param token - String representing the encrypted authentication token
	 * @return - the newly create Manager object
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws - BadRequestException - if the manager parameter could not be instantiated. Usually by bad json.
	 * @throws - UnauthorizedException
	 */
	@PostMapping
	public ResponseEntity<Manager> addManager(@RequestParam("manager") String managerStr, @RequestParam("password") String password, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");
		if(!"class com.revature.project02.models.Admin".equals(uat.getRole())) throw new UnauthorizedException("Unauthorized Access!");
		
		ObjectMapper om = new ObjectMapper();
		Manager manager;
		try {
			manager = om.readValue(managerStr, Manager.class);
		} catch (IOException e) {
			throw new BadRequestException("invalid manager");
		}
		return new ResponseEntity<>(mService.addManager(manager, password), HttpStatus.CREATED);
	}
	
	
	/**
	 * Description - Returns a Manager of the provided driver id
	 * @param id - Integer representation of the driver's id
	 * @param token - String representing the encrypted authentication token 
	 * @return - a Manager
	 * @throws ResouceNotFoundException - If the manager does not exist or cannot be found.
	 */
	@GetMapping(value="/get-manager-driver-{id}")
	public ResponseEntity<Manager> getDriverManager(@PathVariable("id") Integer id, @RequestHeader("token") String token){
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");
		if("class com.revature.project02.models.Driver".equals(uat.getRole()))
		{
			if (uat.getUserId().equals(id)) return new ResponseEntity<>(mService.getManagerByDriver(dService.getDriverById(id)), HttpStatus.OK);
			else throw new UnauthorizedException("Unauthorized Access!");
		}
		else if("class com.revature.project02.models.Manager".equals(uat.getRole())) return new ResponseEntity<>(mService.getManagerByDriver(dService.getDriverById(id)), HttpStatus.OK);
		else if("class com.revature.project02.models.Admin".equals(uat.getRole())) return new ResponseEntity<>(mService.getManagerByDriver(dService.getDriverById(id)), HttpStatus.OK);
		else throw new UnauthorizedException("Unauthorized Access!");
		
	}
	
	/**
	 * Description - Returns a List of all managers in the db table
	 * @param token - String representing the encrypted authentication token
	 * @return - a List of all managers in the db table
	 * @throws - ResourceNotFoundException - if there are no managers in the table
	 */
	@GetMapping
	public ResponseEntity<List<Manager>> getAllManagers(@RequestHeader("token") String token){
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");
		if(!"class com.revature.project02.models.Admin".equals(uat.getRole())) throw new UnauthorizedException("Unauthorized Access!");

		return new ResponseEntity<>(mService.getAllManagers(), HttpStatus.OK);
	}
	
	/**
	 * Description - Deletes the requested manager
	 * @param id - Integer representation of the manager's id
	 * @param token - String representing the encrypted authentication token
	 * @return - HttpStatus of ok
	 * @throws - BadRequestException
	 * @throws UnauthorizedException
	 */
	@DeleteMapping(value = "/managerid-{id}")
	public HttpStatus deleteManager(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");
		if(!"class com.revature.project02.models.Admin".equals(uat.getRole())) throw new UnauthorizedException("Unauthorized Access!");

		mService.deleteManager(id);
		return HttpStatus.OK;
	}
	
	
}
