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
import com.revature.project02.repositories.ManagerRepository;
import com.revature.project02.services.ManagerService;
import com.revature.project02.util.HashUtil;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	//Class attributes
	@Autowired
	private ManagerRepository mRepo;

	@Override
	public List<Manager> getAllManagers() {
		return mRepo.findAll();
	}

	@Override
	public Manager getManagerById(Integer id) {
		Optional<Manager> result = mRepo.findById(id);
		if(!result.isPresent())
			throw new ResourceNotFoundException("Manager of id:" + id + " cannot be found");
		return result.get();
	}

	@Override
	public Manager getManagerByRoute(Route route) {
		if(route == null)
			throw new BadRequestException("The route specified could not be instantiated,");
		Manager temp = mRepo.getManagerByRoutes(route);
		
		if(temp == null)
			throw new ResourceNotFoundException("This route does not have an assigned manager");
		return temp;
	}

	@Override
	public Manager getManagerByDriver(Driver driver) {
		return mRepo.getManagerByDrivers(driver);
	}

	@Override
	public Manager addManager(Manager manager, String password) {
		manager.setPassHash(HashUtil.hashStr(password));
		return mRepo.save(manager);
	}

	@Override
	public boolean deleteManager(Manager manager) {
		try {
			mRepo.delete(manager);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public Manager updateManager(Manager manager) {
		return mRepo.save(manager);
	}

	@Override
	public boolean deleteManager(Integer id) {
		try {
			mRepo.deleteById(id);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public Manager getManagerByUsername(String username) {
		return mRepo.getManagerByUsername(username);
	}
	
	
	
}
