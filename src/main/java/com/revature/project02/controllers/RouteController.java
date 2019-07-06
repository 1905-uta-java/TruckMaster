package com.revature.project02.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project02.models.Route;
import com.revature.project02.services.RouteService;

@RestController
@RequestMapping("/routes")
public class RouteController {
	
	@Autowired
	private RouteService routeService;
	
	@PostMapping
	public ResponseEntity<Route> addRoute(HttpServletRequest request) {
		
		ObjectMapper om = new ObjectMapper();
		
		Route route;
		
		String routeString = request.getParameter("route");
		
		System.out.println("Route string: " + routeString);
		
		if(routeString == null || routeString.length() == 0)
			return new ResponseEntity<Route>(HttpStatus.BAD_REQUEST);
		
		try {
			
			route = om.readValue(routeString, Route.class);
			
		} catch (IOException e) {
			
			return new ResponseEntity<Route>(HttpStatus.BAD_REQUEST);
		}
		
		if(route == null)
			return new ResponseEntity<Route>(HttpStatus.BAD_REQUEST);
		
		Route createdRoute = routeService.createRoute(route);
		
		if(createdRoute == null)
			return new ResponseEntity<Route>(HttpStatus.CONFLICT);
		
		return new ResponseEntity<Route>(createdRoute, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Route> getRouteById(@PathVariable("id") Integer id) {
		
		Route route = routeService.getRoute(id);
		
		if(route == null)
			return new ResponseEntity<Route>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Route>(route, HttpStatus.OK);
	}
}
