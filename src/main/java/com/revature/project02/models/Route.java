package com.revature.project02.models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@JsonIgnoreProperties({"manager", "driver"})
public class Route {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routeSequence")
	@SequenceGenerator(name = "routeSequence", sequenceName = "SQ_ROUTE_PK")
	@Column(name = "ROUTE_ID")
	private Integer id;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "START_TIME")
	private Timestamp idealStartTime;
	
	@ManyToOne
	@JoinColumn(name = "MANAGER_ID")
	private Manager manager;
	
	@ManyToOne
	@JoinColumn(name = "DRIVER_ID")
	private Driver driver;
	
	@OneToMany(
			mappedBy = "route",
			cascade = CascadeType.ALL, 
			fetch = FetchType.EAGER)
	private List<RouteNode> nodes;
	
	public Route() {
		super();
	}
	
	public Route(Integer id, String description, Timestamp idealStartTime, Manager manager, Driver driver,
			List<RouteNode> nodes) {
		super();
		this.id = id;
		this.description = description;
		this.idealStartTime = idealStartTime;
		this.manager = manager;
		this.driver = driver;
		this.nodes = nodes;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Timestamp getIdealStartTime() {
		return idealStartTime;
	}
	
	public void setIdealStartTime(Timestamp idealStartTime) {
		this.idealStartTime = idealStartTime;
	}
	
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public Driver getDriver() {
		return driver;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public List<RouteNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<RouteNode> nodes) {
		this.nodes = nodes;
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
		Route other = (Route) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Route [id=" + id + ", description=" + description + ", idealStartTime=" + idealStartTime + "]";
	}
}