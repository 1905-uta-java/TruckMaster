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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.models.Route;
import com.revature.project02.services.DriverService;
import com.revature.project02.services.ManagerService;
import com.revature.project02.services.RouteService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/routes")
public class RouteController {
	@Autowired
	private ManagerService mService;
	
	@Autowired
	private DriverService dService;
	
	@Autowired
	private RouteService routeService;
	
	@PostMapping
	public ResponseEntity<Route> addRoute(@RequestBody Route route) {
		
		return new ResponseEntity<>(routeService.createRoute(route), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Route> getRouteById(@PathVariable("id") Integer id) {
		
		Route route = routeService.getRoute(id);
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(route, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/stub")
	public ResponseEntity<Route> getRouteStubById(@PathVariable("id") Integer id) {
		
		Route route = routeService.getRoute(id);
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		route.setNodes(null);
		
		return new ResponseEntity<>(route, HttpStatus.OK);
	}
	
	/**
	 * Description - Update the requested route
	 * @param route - json of the route being updated
	 * @return - the json of the updated route
	 * @throws - nothing
	 */
	@PutMapping
	public ResponseEntity<Route> updateRoute(@RequestBody Route route) {
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Route updatedRoute = routeService.editRoute(route);
		
		if(updatedRoute == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
	}
	
	/**
	 * Description - Delete the requested route
	 * @param id - Integer representation of the route id of the route being deleted
	 * @return - The json of the deleted route.
	 * @throws - nothing
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Route> deleteRoute(@PathVariable("id") Integer id) {
		
		Route route = routeService.deleteRoute(id);
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(route, HttpStatus.OK);
	}
	
	/**
	 * Description - Get a list of all routes assigned to the driver
	 * @param id - Integer representation of the driver's id
	 * @return - a List of routes assigned to driver
	 * @throws ResourceNotFoundException - if the driver has no routes.
	 */
	@GetMapping(value="/get-routes-driver-{id}")
	public ResponseEntity<List<Route>> getAllRoutesForDriver(@PathVariable("id") Integer id){
		return new ResponseEntity<>(routeService.getRoutesByDriver(dService.getDriverById(id)), HttpStatus.OK);
	}
	
	/**
	 * Description - Gets all routes assigned to this manager
	 * @param id - the Integer representation of the manager's id
	 * @return - a List of routes under this manager
	 * @throws ResourceNotFoundException - if there are no routes under this manager or if this manager doesn't exist
	 */
	@GetMapping(value = "/get-all-routes+managerid-{id}")
	public ResponseEntity<List<Route>> getAllRoutesForManager(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(routeService.getRoutesByManager(mService.getManagerById(id)), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Route>> getAllRoutes(){
		return new ResponseEntity<>(routeService.getAllRoutes(), HttpStatus.OK);
	}
}
