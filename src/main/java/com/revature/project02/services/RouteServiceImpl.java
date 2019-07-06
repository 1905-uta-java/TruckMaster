package com.revature.project02.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.models.Route;
import com.revature.project02.repositories.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService {
	
	@Autowired
	RouteRepository routeRepo;
	
	/*
	 * returns the newly created route
	 * (with it's generated id)
	 */
	@Override
	public Route createRoute(Route route) {
		
		if(routeRepo.existsById(route.getId()))
			return null;
		
		return routeRepo.save(route);
	}

	@Override
	public Route getRoute(Integer routeId) {
		
		Optional<Route> result = routeRepo.findById(routeId);
		return (result.isPresent()) ? result.get() : null;
	}
}
