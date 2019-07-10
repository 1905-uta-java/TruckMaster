package com.revature.project02.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
	
	public List<Route> getRoutesByDriver(Driver driver);
	public List<Route> getRoutesByManager(Manager manager);
}
