package com.revature.project02.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.models.Route;
import com.revature.project02.models.RouteNode;
import com.revature.project02.services.RouteService;

@RestController
@RequestMapping("/routes")
public class RouteController {
	
	@Autowired
	private RouteService routeService;
	
	@PostMapping
	public ResponseEntity<Route> addRoute(HttpServletRequest request) {
		
		ObjectMapper om = new ObjectMapper();
		
		String routeString = request.getParameter("route");
		
		if(routeString == null || routeString.length() == 0) {
			
			throw new BadRequestException("Missing Route Parameter");
		}
		
		Route route = null;
		
		try {
			
			route = om.readValue(routeString, Route.class);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			throw new BadRequestException("invalid Route");
		}
		
		if(route == null)
			throw new BadRequestException("The given route was null");
		
		String nodesString = request.getParameter("nodes");
		
		List<RouteNode> nodes = new ArrayList<>();
		
		if(nodesString != null && nodesString.length() > 0) {
			
			try {
				
				nodes = om.readValue(nodesString, new TypeReference<List<RouteNode>>(){});
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
				throw new BadRequestException("Invalid Node list");
			}
			
			for(int i = 0; i < nodes.size(); i++) {
				
				nodes.get(i).setOrder(i);
			}
		}
		
		route.setNodes(nodes);
		
		return new ResponseEntity<Route>(routeService.createRoute(route), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Route> getRouteById(@PathVariable("id") Integer id) {
		
		Route route = routeService.getRoute(id);
		
		if(route == null)
			return new ResponseEntity<Route>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Route>(route, HttpStatus.OK);
	}
}
