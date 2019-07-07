package com.revature.project02.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.models.Route;
import com.revature.project02.models.RouteNode;
import com.revature.project02.repositories.RouteNodeRepository;
import com.revature.project02.repositories.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService {
	
	@Autowired
	RouteRepository routeRepo;
	
	@Autowired
	RouteNodeRepository nodeRepo;
	
	/*
	 * returns the newly created route
	 * (with it's generated id)
	 */
	@Override
	public Route createRoute(Route route) {
		
		if(route == null)
			return null;
		
		List<RouteNode> nodes = route.getNodes();
		
		route.setNodes(null);
		
		Route savedRoute = routeRepo.save(route);
		
		if(nodes != null && nodes.size() > 0) {
			for(int i = 0; i < nodes.size(); i++) {
				nodes.get(i).setRoute(savedRoute);
				nodes.set(i, nodeRepo.save(nodes.get(i)));
			}
		}
		
		savedRoute.setNodes(nodes);
		
		return savedRoute;
	}
	
	@Override
	public Route getRoute(Integer routeId) {
		
		Optional<Route> result = routeRepo.findById(routeId);
		return (result.isPresent()) ? result.get() : null;
	}
}
