package com.revature.project02;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.project02.models.Route;
import com.revature.project02.services.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteServiceTests {
	
	@Autowired
	private RouteService routeService;
	
	// createRoute() test
	
	@Test
	public void createRouteTest() {
		
		Route route = new Route();
		route.setDescription("Test Description");
		route.setIdealStartTime(new Timestamp(0));
		
		Route savedRoute = routeService.createRoute(route);
		
		savedRoute = routeService.getRoute(savedRoute.getId());
		
		if(savedRoute == null)
			fail();
		
		if(!savedRoute.getDescription().contentEquals(route.getDescription()))
			fail();
		
		assertEquals(route.getIdealStartTime(), savedRoute.getIdealStartTime());
	}
}
