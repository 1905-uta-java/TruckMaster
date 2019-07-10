package com.revature.project02.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project02.models.Route;
import com.revature.project02.models.RouteNode;

@Repository
public interface RouteNodeRepository extends JpaRepository<RouteNode, Integer> {
	public List<RouteNode> getNodesByRouteId(Route route);
}
