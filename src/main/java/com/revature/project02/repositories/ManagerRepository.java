package com.revature.project02.repositories;
//imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{
	
	//methods
	public Manager getManagerById(Integer id);
	public Manager getManagerByRoutes(Route route);
	public Manager getManagerByDrivers(Driver driver);
	public Manager getManagerByUsername(String username);
}
