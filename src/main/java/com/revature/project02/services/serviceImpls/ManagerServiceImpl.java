package com.revature.project02.services.serviceImpls;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Route;
import com.revature.project02.services.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	//Class attributes

	@Override
	public List<Driver> getAllDrivers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver getDriverByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver addDriver(Driver d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver fireDriver(Driver d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int assignRouteToDriver(Driver d, Route r) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
