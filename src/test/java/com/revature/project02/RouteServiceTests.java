package com.revature.project02;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.project02.models.Route;
import com.revature.project02.models.RouteNode;
import com.revature.project02.services.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteServiceTests {
	
	@Autowired
	private RouteService routeService;
	
	// createRoute() test
	@Test
	public void createRouteTest() {
		
		// because of the nature of the crud methods, I can't effectively test each separately,
		// beacuse of this, the getRoute method is basically tested every time another is tested
		// as well as the createRoute method
		
		Route route = new Route();
		route.setDescription("Test Description");
		route.setIdealStartTime(new Timestamp(0));
		
		List<RouteNode> nodes = new ArrayList<RouteNode>();
		nodes.add(new RouteNode(0, "Home", route, 0));
		nodes.add(new RouteNode(0, "Work", route, 0));
		
		route.setNodes(nodes);
		
		Route savedRoute = routeService.createRoute(route);
		
		savedRoute = routeService.getRoute(savedRoute.getId());
		
		if(savedRoute == null)
			fail();
		
		if(!route.getDescription().equals(savedRoute.getDescription()))
			fail();
		
		if(savedRoute.getNodes() == null || savedRoute.getNodes().isEmpty())
			fail();
		
		List<RouteNode> savedNodes = savedRoute.getNodes();
		savedNodes.sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		if(savedNodes == null || nodes.size() != savedNodes.size())
			fail();
		
		for(int i = 0; i < nodes.size(); i++) {
			
			if(!nodes.get(i).getLocation().equals(savedNodes.get(i).getLocation()))
				fail();
		}
		
		assertEquals(route.getIdealStartTime(), savedRoute.getIdealStartTime());
		
		routeService.deleteRoute(savedRoute.getId());
	}
	
	@Test
	public void editRouteAddNodeTest() {
		
		Route route = new Route();
		route.setDescription("Test Description");
		route.setIdealStartTime(new Timestamp(0));
		
		List<RouteNode> nodes = new ArrayList<RouteNode>();
		nodes.add(new RouteNode(0, "Home", route, 0));
		nodes.add(new RouteNode(0, "Work", route, 0));
		
		route.setNodes(nodes);
		
		Route savedRoute = routeService.createRoute(route);
		
		savedRoute.getNodes().add(new RouteNode(0, "Vacation", savedRoute, 0));
		
		Route updatedRoute = routeService.editRoute(savedRoute);
		
		List<RouteNode> savedNodes = savedRoute.getNodes();
		savedNodes.sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		List<RouteNode> updatedNodes = updatedRoute.getNodes();
		updatedNodes.sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		for(int i = 0; i < nodes.size(); i++) {
			
			if(!savedNodes.get(i).getLocation().equals(updatedNodes.get(i).getLocation()))
				fail();
		}
		
		assertTrue(true);
		
		routeService.deleteRoute(updatedRoute.getId());
	}
	
	@Test
	public void editRouteRemoveNodeTest() {
		
		Route route = new Route();
		route.setDescription("Test Description");
		route.setIdealStartTime(new Timestamp(0));
		
		List<RouteNode> nodes = new ArrayList<RouteNode>();
		nodes.add(new RouteNode(0, "Home", route, 0));
		nodes.add(new RouteNode(0, "Work", route, 0));
		nodes.add(new RouteNode(0, "Vacation", route, 0));
		
		route.setNodes(nodes);
		
		Route savedRoute = routeService.createRoute(route);
		
		savedRoute.getNodes().sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		savedRoute.getNodes().remove(2);
		
		Route updatedRoute = routeService.editRoute(savedRoute);
		
		List<RouteNode> savedNodes = savedRoute.getNodes();
		savedNodes.sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		List<RouteNode> updatedNodes = updatedRoute.getNodes();
		updatedNodes.sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		for(int i = 0; i < nodes.size(); i++) {
			
			if(!savedNodes.get(i).getLocation().equals(updatedNodes.get(i).getLocation()))
				fail();
		}
		
		assertTrue(true);
		
		routeService.deleteRoute(updatedRoute.getId());
	}
	
	@Test
	public void editRouteEditNodeTest() {
		
		Route route = new Route();
		route.setDescription("Test Description");
		route.setIdealStartTime(new Timestamp(0));
		
		List<RouteNode> nodes = new ArrayList<RouteNode>();
		nodes.add(new RouteNode(0, "Home", route, 0));
		nodes.add(new RouteNode(0, "Work", route, 0));
		nodes.add(new RouteNode(0, "Vacation", route, 0));
		
		route.setNodes(nodes);
		
		Route savedRoute = routeService.createRoute(route);
		
		savedRoute.getNodes().sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		RouteNode nodeToEdit = savedRoute.getNodes().get(2);
		nodeToEdit.setLocation("Somewhere out there");
		savedRoute.getNodes().set(2, nodeToEdit);
		
		Route updatedRoute = routeService.editRoute(savedRoute);
		
		List<RouteNode> savedNodes = savedRoute.getNodes();
		savedNodes.sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		List<RouteNode> updatedNodes = updatedRoute.getNodes();
		updatedNodes.sort((node1, node2) -> node1.getOrder() - node2.getOrder());
		
		for(int i = 0; i < nodes.size(); i++) {
			
			if(!savedNodes.get(i).getLocation().equals(updatedNodes.get(i).getLocation()))
				fail();
		}
		
		assertTrue(true);
		
		routeService.deleteRoute(updatedRoute.getId());
	}
}
