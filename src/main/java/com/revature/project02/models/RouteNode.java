package com.revature.project02.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@JsonIgnoreProperties({"route"})
public class RouteNode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routeNodeSequence")
	@SequenceGenerator(name = "routeNodeSequence", sequenceName = "SQ_ROUTE_NODE_PK")
	@Column(name = "ROUTE_NODE_ID")
	private Integer id;
	
	@Column(name = "GEO_LOC")
	private String location;
	
	@ManyToOne
	@JoinColumn(name = "ROUTE_ID")
	private Route route;
	
	@Column(name = "ORDER_POS")
	private int order;
	
	public RouteNode() {
		super();
	}
	
	public RouteNode(Integer id, String location, Route route, int order) {
		super();
		this.id = id;
		this.location = location;
		this.route = route;
		this.order = order;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Route getRoute() {
		return route;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RouteNode other = (RouteNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RouteNode [id=" + id + ", location=" + location + ", route=" + route + ", order=" + order + "]";
	}
}