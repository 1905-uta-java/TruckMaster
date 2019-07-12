package com.revature.project02.services.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.exceptions.ResourceNotFoundException;
import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
import com.revature.project02.models.User;
import com.revature.project02.repositories.DriverRepository;
import com.revature.project02.repositories.ManagerRepository;
import com.revature.project02.repositories.RouteRepository;
import com.revature.project02.services.DriverService;
import com.revature.project02.util.HashUtil;
import com.revature.project02.util.ValidationUtil;

@Service
public class DriverServiceImpl implements DriverService {
	
	//class attributes
	@Autowired
	private DriverRepository dRepo;
	@Autowired
	private RouteRepository rRepo;
	
	/**
	 * Description - Returns a list of all drivers in the Drivers table
	 * @throws - ResourceNotFoundException - if there are no drivers returned
	 */
	@Override
	public List<Driver> getAllDrivers() {
		List<Driver> temp = dRepo.findAll();
		if(temp == null)
			throw new ResourceNotFoundException("No drivers found.");
		return temp;
	}
	
	@Override
	public Driver getDriverById(Integer id) {
		Optional<Driver> result = dRepo.findById(id);
		
		return (result.isPresent()) ? result.get():null;
	}

	@Override
	public Driver addDriver(Driver d, Manager manager, String password) {
		if(d == null)
			throw new BadRequestException("Driver not instantiated");
		
		Optional<Driver> driver = dRepo.findById(d.getId());
		
		if(driver.isPresent())
			throw new BadRequestException("Driver already exists.");
		
		if(manager == null
			|| !ValidationUtil.validUsername(d.getUsername())
			|| !ValidationUtil.validPassword(password)
			|| !ValidationUtil.validEmail(d.getEmail())
			|| !ValidationUtil.validPhone(d.getPhone()))
			throw new BadRequestException("Invalid data.");
		
		d.setManager(manager);
		d.setPassHash(HashUtil.hashStr(password));
		return dRepo.save(d);
	}
	
	@Override
	public Driver addDriver(Driver d) {
		if(!ValidationUtil.validUsername(d.getUsername())
				|| !ValidationUtil.validEmail(d.getEmail())
				|| !ValidationUtil.validPhone(d.getPhone()))
				throw new BadRequestException("Invalid data.");

		return dRepo.save(d);
	}

	@Override
	public void mutchDriver(Driver d) {
		if(d == null)
			throw new BadRequestException("Driver could not be instantiated.");
		dRepo.delete(d);
	}
	
	/**
	 * 
	 */
	@Override
	public List<Driver> getDriversByManager(Manager manager) {
		if(manager == null)
			throw new BadRequestException("Unable to instantiate requested manager");
		List<Driver> temp = dRepo.getDriversByManager(manager);
		
		if(temp == null)
			throw new ResourceNotFoundException("No drivers found for the requested manager id: " + manager.getId());
		
		return temp;
	}

	@Override
	public Driver updateDriver(Driver driver) {
		if(!ValidationUtil.validUsername(driver.getUsername())
				|| !ValidationUtil.validEmail(driver.getEmail())
				|| !ValidationUtil.validPhone(driver.getPhone()))
				throw new BadRequestException("Invalid data.");

		return dRepo.save(driver);
	}

	@Override
	public Driver getDriverByUsername(String username) {
		Driver result = dRepo.getDriverByUsername(username);
		if(result == null)
			throw new ResourceNotFoundException("No such driver with: " + username + " username.");
		return result;
	}
	
	public Driver getDriverByRoute(Integer routeId) {
		
		Optional<Route> r = rRepo.findById(routeId);
		
		if(!r.isPresent())
			throw new BadRequestException("No such route.");
		
		Driver d = dRepo.getDriverByRoutes(r.get());
		
		if(d == null)
			return null;
		
		return d;
	}

	@Override
	public void mutchDriver(Integer id) {
		Optional<Driver> temp = dRepo.findById(id);
		if(!temp.isPresent())
			throw new BadRequestException("Invalid id");
		
		dRepo.delete(temp.get());
	}
}
