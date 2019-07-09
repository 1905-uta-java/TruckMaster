package com.revature.project02.services.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.repositories.DriverRepo;
import com.revature.project02.services.DriverService;

@Service
public class DriverServiceImpl implements DriverService {
	
	//class attributes
	@Autowired
	private DriverRepo dRepo;

	@Override
	public List<Driver> getAllDrivers() {
		return dRepo.findAll();
	}

	@Override
	public Driver getDriverById(Integer id) {
		Optional<Driver> result = dRepo.findById(id);
		
		return (result.isPresent()) ? result.get():null;
	}

	@Override
	public Driver addDriver(Driver d) {
		return dRepo.save(d);
	}

	@Override
	public Driver updateDriver(Driver d) {
		return dRepo.save(d);
	}

	@Override
	public boolean deleteDriver(Driver d) {
		try {
			dRepo.delete(d);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public List<Manager> getManagers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Route> getRoutes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route getRoute(Integer routeId) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
