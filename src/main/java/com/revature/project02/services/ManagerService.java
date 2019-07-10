package com.revature.project02.services;
//imports
import java.util.List;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.Route;
/**
 * Description - Interface for Managers methods
 * @author mattd
 * @version 07/09/2019
 */
public interface ManagerService {
	public List<Manager> getAllManagers();
	public Manager getManagerById(Integer i);
	public Manager getManagerByRoute(Route route);
	public Manager getManagerByDriver(Driver driver);
	public Manager getManagerByUsername(String username);
	public Manager addManager(Manager manager);
	public Manager updateManager(Manager manager);
	public boolean deleteManager(Manager manager);
	public boolean deleteManager(Integer id);
}
