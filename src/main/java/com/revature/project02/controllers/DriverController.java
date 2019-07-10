package com.revature.project02.controllers;
import org.springframework.beans.factory.annotation.Autowired;
//imports
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.models.Driver;
import com.revature.project02.services.DriverService;
import com.revature.project02.services.ManagerService;

/**
 * Description - Controller for driver requests
 * @author mattD, joshuaV, wolfeM
 * @version 07/09/2019
 */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/driver")
public class DriverController {//class header
	//class attributes
	@Autowired
	private DriverService dService;
	@Autowired
	private ManagerService mService;
	
	
	
	//most likely do not need this section of get requests because this should be handled from users... need to confirm
	@GetMapping(value="/driverid_{id}")
	public Driver getDriver(@PathVariable("id") Integer id) {
		return dService.getDriverById(id);
	}
	
	@GetMapping(value="/drivername_{drivername}")
	public Driver getDriver(@PathVariable("drivername") String drivername) {
		return dService.getDriverByUsername(drivername);
	}
	
	@PostMapping(value="/add-driver-manager_id= {id}")
	public Driver addDriver(@PathVariable("id") Integer id, @RequestBody Driver d) {
		return dService.addDriver(d, mService.getManagerById(id));
	}
	
}//end of class
