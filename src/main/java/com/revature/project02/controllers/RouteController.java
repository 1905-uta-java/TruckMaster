package com.revature.project02.controllers;

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
import com.revature.project02.services.RouteService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/routes")
public class RouteController {
	
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
	
	@PutMapping
	public ResponseEntity<Route> updateRoute(@RequestBody Route route) {
		
		Route updatedRoute = routeService.editRoute(route);
		
		return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Route> deleteRoute(@PathVariable("id") Integer id) {
		
		Route route = routeService.deleteRoute(id);
		
		if(route == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(route, HttpStatus.OK);
	}
}
