package com.revature.project02.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;

@Service
public interface RouteService {
	
	/*
	 * returns the newly created route
	 * (with it's generated id)
	 */
	public Route createRoute(Route route);
	
	public Route getRoute(Integer routeId);
	
	/*
	 * returns the new state of the edited route
	 */
	public Route editRoute(Route route);
	
	/*
	 * returns the route that was deleted, or null if it
	 * didn't exist in the first place
	 */
	public Route deleteRoute(Integer routeId);
	
	public List<Route> getRoutesByManager(Manager manager);
	public List<Route> getRoutesByDriver(Driver driver);
	public List<Route> getAllRoutes();
	
	public Route assignDriverToRoute(Integer driverId, Route route);
}
