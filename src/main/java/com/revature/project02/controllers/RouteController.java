package com.revature.project02.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.exceptions.ResourceNotFoundException;
import com.revature.project02.exceptions.UnauthorizedException;
import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;
import com.revature.project02.services.DriverService;
import com.revature.project02.services.ManagerService;
import com.revature.project02.services.RouteService;
import com.revature.project02.services.UserService;
import com.revature.project02.util.AuthTokenUtil;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/routes")
public class RouteController {
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private ManagerService mService;
	
	@Autowired
	private DriverService dService;
	
	@Autowired
	private RouteService routeService;
	
	/**
	 * Description - add a route
	 * @param route - route to be added
	 * @param token - String representing the encrypted token
	 * @return json of the added route
	 * @throws UnauthorizedException
	 */
	@PostMapping
	public ResponseEntity<Route> addRoute(@RequestBody Route route, @RequestHeader("token") String token) {
		
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		
		if(uat == null)
			throw new UnauthorizedException("Unauthorized Access!");	
		
		User managerUser = uService.getUserById(uat.getUserId(), uat);
		
		if(managerUser instanceof Manager) {
			
			route.setManager((Manager) managerUser);
			
			return new ResponseEntity<>(routeService.createRoute(route), HttpStatus.CREATED);
		}
		
		throw new UnauthorizedException("Unauthorized Access!");
	}
	
	/**
	 * Description - gets a route by a route id
	 * @param id - id of the route
	 * @param token - string representing the encrypted token
	 * @return json of the id
	 * @throws UnauthorizedException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Route> getRouteById(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");		
		
		Route route = routeService.getRoute(id);		
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		if(uat.getUserId().equals(route.getDriver().getId()) || uat.getUserId().equals(route.getManager().getId())
			|| "class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			return new ResponseEntity<>(route, HttpStatus.OK);			
		}
		throw new UnauthorizedException("Unauthorized Access!");
	}
	
	/**
	 * Description - gets a route stub by id
	 * @param id - id of the route
	 * @param token - string representing the encrypted token
	 * @return json of the stub
	 * @throws UnauthorizedException
	 */
	@GetMapping(value = "/{id}/stub")
	public ResponseEntity<Route> getRouteStubById(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");		
		
		Route route = routeService.getRoute(id);
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		if(uat.getUserId().equals(route.getDriver().getId()) || uat.getUserId().equals(route.getManager().getId())
				|| "class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			route.setNodes(null);
			return new ResponseEntity<>(route, HttpStatus.OK);			
		}
		throw new UnauthorizedException("Unauthorized Access!");
	}
	
	/**
	 * Description - Update the requested route
	 * @param route - json of the route being updated
	 * @param token - string representation of the encrypted token
	 * @return - the json of the updated route
	 * @throws UnauthorizedException
	 */
	@PutMapping
	public ResponseEntity<Route> updateRoute(@RequestBody Route route, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");		
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		if(uat.getUserId().equals(route.getManager().getId())
			|| "class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			//Sorry about the extra db call
			Route original = routeService.getRoute(route.getId());
			if(original == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			original.setDescription(route.getDescription());
			original.setIdealStartTime(route.getIdealStartTime());
			if(route.getDriver() != null) original.setDriver(route.getDriver());
			if(route.getManager() != null) original.setManager(route.getManager());
			original.setNodes(route.getNodes());
			
			Route updatedRoute = routeService.editRoute(original);
			
			if(updatedRoute == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
		}

		throw new UnauthorizedException("Unauthorized Access!");
	}
	
	/**
	 * Description - Delete the requested route
	 * @param id - Integer representation of the route id of the route being deleted
	 * @param token - string representing the encrypted token
	 * @return - The json of the deleted route.
	 * @throws UnauthorizedException
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Route> deleteRoute(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");		

		//sorry about the excess call.
		Route route = routeService.getRoute(id);
		if(uat.getUserId().equals(route.getManager().getId())
				|| "class com.revature.project02.models.Admin".equals(uat.getRole()))
		{
			route = routeService.deleteRoute(id);
			
			if(route == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			return new ResponseEntity<>(route, HttpStatus.OK);
		}
		throw new UnauthorizedException("Unauthorized Access!");
	}
	
	/**
	 * Description - Get a list of all routes assigned to the driver
	 * @param id - Integer representation of the driver's id
	 * @param token - string representing the encrypted token
	 * @return - a List of routes assigned to driver
	 * @throws ResourceNotFoundException - if the driver has no routes.
	 * @throws UnauthorizedException
	 */
	@GetMapping(value="/get-routes-driver-{id}")
	public ResponseEntity<List<Route>> getAllRoutesForDriver(@PathVariable("id") Integer id, @RequestHeader("token") String token){
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");		
		
		//sorry about the excess call.
		Driver d = dService.getDriverById(id);
		if(uat.getUserId().equals(id) || uat.getUserId().equals(d.getManager().getId())
				|| "class com.revature.project02.models.Admin".equals(uat.getRole()))
			return new ResponseEntity<>(routeService.getRoutesByDriver(dService.getDriverById(id)), HttpStatus.OK);
		throw new UnauthorizedException("Unauthorized Access!");
	}
	
	/**
	 * Description - Gets all routes assigned to this manager
	 * @param id - the Integer representation of the manager's id
	 * @param token - string representation of the encrypted token
	 * @return - a List of routes under this manager
	 * @throws UnauthorizedException
	 * @throws ResourceNotFoundException - if there are no routes under this manager or if this manager doesn't exist
	 */
	@GetMapping(value = "/get-all-routes+managerid-{id}")
	public ResponseEntity<List<Route>> getAllRoutesForManager(@PathVariable("id") Integer id, @RequestHeader("token") String token) {
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");		

		if(uat.getUserId().equals(id) || "class com.revature.project02.models.Admin".equals(uat.getRole()))		
			return new ResponseEntity<>(routeService.getRoutesByManager(mService.getManagerById(id)), HttpStatus.OK);
		throw new UnauthorizedException("Unauthorized Access!");
	}
	
	/**
	 * Description - Gets all routes.
	 * @param token - the string representation of the encrypted token
	 * @return - the list of all routes
	 * @throws UnauthorizedException
	 */
	@GetMapping
	public ResponseEntity<List<Route>> getAllRoutes(@RequestHeader("token") String token){
		//Unencrypt the token
		UnencryptedAuthenticationToken uat = AuthTokenUtil.fromEncryptedAuthenticationToken(token);
		if(uat == null) throw new UnauthorizedException("Unauthorized Access!");		
	
		if("class com.revature.project02.models.Admin".equals(uat.getRole()))
			return new ResponseEntity<>(routeService.getAllRoutes(), HttpStatus.OK);
		throw new UnauthorizedException("Unauthorized Access!");
	}
}
