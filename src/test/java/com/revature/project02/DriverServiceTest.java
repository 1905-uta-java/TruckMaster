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
import com.revature.project02.util.HashUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckMasterRunner.class)
public class DriverServiceTest {
	@Autowired
	private DriverService dService;
	
	@Test
	public void getDriverByIdTest() {
		Driver driver = new Driver();
		driver.setUsername("johnny");
		driver.setId(2501);
		driver.setEmail("validb@email.com");
		driver.setPassHash(HashUtil.hashStr("validPassword"));
		driver.setPhone("(702) 318-3392");

		
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
		
		driver.setUsername("johnnya");
		driver2.setUsername("abbyab");
		driver3.setUsername("marycd");

		driver.setEmail("validc@email.com");
		driver.setPassHash(HashUtil.hashStr("validPassword"));
		driver.setPhone("(702) 358-3192");
		driver3.setEmail("validd@email.com");
		driver3.setPassHash(HashUtil.hashStr("validPassword"));
		driver3.setPhone("(702) 358-3292");
		driver2.setEmail("valide@email.com");
		driver2.setPassHash(HashUtil.hashStr("validPassword"));
		driver2.setPhone("(702) 358-3312");

		
		
		driver = dService.addDriver(driver);
		driver2 = dService.addDriver(driver2);
		driver3 = dService.addDriver(driver3);
		
		List<Driver> tempList = dService.getAllDrivers();
		
		dService.mutchDriver(driver);
		dService.mutchDriver(driver2);
		dService.mutchDriver(driver3);
		
		List<Driver> tempList2 = dService.getAllDrivers();
		int tempSize = tempList2.size() + 3;
		
		assertEquals(tempList.size(), tempSize);
		
	}
	

}
