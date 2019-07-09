package com.revature.project02.services.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.models.User;
import com.revature.project02.repositories.DriverRepository;
import com.revature.project02.repositories.ManagerRepository;
import com.revature.project02.services.DriverService;

@Service
public class DriverServiceImpl implements DriverService {
	
	//class attributes
	@Autowired
	private DriverRepository dRepo;

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
	public Driver addDriver(Driver d, Manager manager) {
		d.setManager(manager);
		return dRepo.save(d);
	}
	
	@Override
	public Driver addDriver(Driver d) {
		return dRepo.save(d);
	}

	@Override
	public boolean mutchDriver(Driver d) {
		try {
			dRepo.delete(d);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public List<Driver> getDriversByManager(Manager manager) {
		return dRepo.getDriversByManager(manager);
	}

	@Override
	public Driver updateDriver(Driver driver) {
		return dRepo.save(driver);
	}

	@Override
	public Driver getDriverByUsername(String username) {
		Driver result = dRepo.getDriverByUsername(username);
		
		return result;
	}

	@Override
	public boolean mutchDriver(Integer id) {
		try {
			dRepo.deleteById(id);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	

}
