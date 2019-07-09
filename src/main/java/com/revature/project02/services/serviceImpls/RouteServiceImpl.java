package com.revature.project02.services.serviceImpls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.models.Route;
import com.revature.project02.models.RouteNode;
import com.revature.project02.repositories.RouteNodeRepository;
import com.revature.project02.repositories.RouteRepository;
import com.revature.project02.services.RouteService;

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
		
		// by setting the id's null here, I'm guaranteeing that 
		// this won't override other records
		route.setId(null);
		
		if(route.getNodes() != null && !route.getNodes().isEmpty()) {
			
			List<RouteNode> nodes = route.getNodes();
			
			for(int i = 0; i < nodes.size(); i++) {
				
				// this sets the nodes' id null to make sure they don't
				// override an existing node in the db
				nodes.get(i).setId(null);
				nodes.get(i).setRoute(route);
				nodes.get(i).setOrder(i);
			}
		}
		
		return routeRepo.save(route);
	}
	
	@Override
	public Route getRoute(Integer routeId) {
		
		Optional<Route> result = routeRepo.findById(routeId);
		if(!result.isPresent())
			return null;
		
		Route route = result.get();
		
		if(route.getNodes() != null && !route.getNodes().isEmpty())
			route.getNodes().sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		return route;
	}
	
	/*
	 * returns the new state of the edited route
	 * or null if the route doesn't exist
	 */
	@Override
	public Route editRoute(Route route) {
		
		if(route == null)
			return null;
		
		Route existingRoute = getRoute(route.getId());
		
		if(existingRoute == null)
			return null;
		
		// this code is meant to remove nodes that are no longer used by this route
		// without this code, it would be impossible to remove nodes from a route
		// and every time we attempt to change a node, it actually creates a new one and
		// keeps the old one
		if(existingRoute.getNodes() != null && !existingRoute.getNodes().isEmpty()) {
			
			List<RouteNode> nodes = new ArrayList<RouteNode>(existingRoute.getNodes());
			
			for(RouteNode node : nodes) {
				
				if(!route.getNodes().contains(node)) {
					
					existingRoute.getNodes().remove(node);
					node.setRoute(null);
					nodeRepo.delete(node);
				}
			}
		}
		
		// make sure the links are maintained for each node
		if(route.getNodes() != null && !route.getNodes().isEmpty()) {
			
			List<RouteNode> nodes = route.getNodes();
			
			for(int i = 0; i < nodes.size(); i++) {
				nodes.get(i).setRoute(route);
				nodes.get(i).setOrder(i);
				nodes.set(i, nodeRepo.save(route.getNodes().get(i)));
			}
		}
		
		// then save the updated state of the route
		return routeRepo.save(route);
	}
	
	/*
	 * returns the route that was deleted, or null if it
	 * didn't exist in the first place
	 */
	@Override
	public Route deleteRoute(Integer routeId) {
		
		// only attempts to perform the operation if the
		// specified Route exists in the DB
		if(routeRepo.existsById(routeId)) {
			
			Route savedRoute = routeRepo.getOne(routeId);
			routeRepo.delete(savedRoute);
			return savedRoute;
		}
		
		return null;
	}
}
