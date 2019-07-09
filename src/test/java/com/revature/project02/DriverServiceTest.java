package com.revature.project02;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.project02.TruckMasterRunner;
import com.revature.project02.models.Driver;
import com.revature.project02.services.DriverService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckMasterRunner.class)
public class DriverServiceTest {
	@Autowired
	private DriverService dService;
	
	@Test
	public void getDriverByIdTest() {
		Driver driver = new Driver();
		driver.setUsername("johnny");
		
		driver = dService.addDriver(driver);
		Driver driver2 = dService.getDriverByUsername("johnny");
		dService.mutchDriver(driver);
		
		assertEquals(driver.getId(), driver2.getId());
		
	}
	
	@Test
	public void getAllDrivers() {
		Driver driver = new Driver();
		Driver driver2 = new Driver();
		Driver driver3 = new Driver();
		
		driver.setUsername("john");
		driver2.setUsername("abby");
		driver3.setUsername("mary");
		
		driver = dService.addDriver(driver);
		driver2 = dService.addDriver(driver2);
		driver3 = dService.addDriver(driver3);
		
		List<Driver> tempList = dService.getAllDrivers();
		
		dService.mutchDriver(driver);
		dService.mutchDriver(driver2);
		dService.mutchDriver(driver3);
		
		assertEquals(tempList.size(), 3);
		
	}
	

}
