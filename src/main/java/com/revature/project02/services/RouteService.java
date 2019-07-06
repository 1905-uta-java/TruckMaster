package com.revature.project02.services;

import org.springframework.stereotype.Service;

import com.revature.project02.models.Route;

@Service
public interface RouteService {
	
	/*
	 * returns the newly created route
	 * (with it's generated id)
	 */
	public Route createRoute(Route route);
	
	public Route getRoute(Integer routeId);
}
