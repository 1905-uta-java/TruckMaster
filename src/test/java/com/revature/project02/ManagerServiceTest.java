package com.revature.project02;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.services.ManagerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckMasterRunner.class)
public class ManagerServiceTest {
	
	@Autowired
	private ManagerService mService;
	
	@Test
	public void getManagerByUsername() {
		Manager manager = new Manager();
		manager.setUsername("johnny");
		
		mService.addManager(manager);
		
		Manager manager2 = mService.getManagerByUsername("johnny");
		
		mService.deleteManager(manager);
		
		assertEquals(manager.getId(), manager2.getId());
		
	}
	
	@Test
	public void getManagerById() {
		
		Manager manager = new Manager();
		manager.setUsername("johnny");
		
		Manager manager2 = mService.addManager(manager);
		manager2 = mService.getManagerById(manager2.getId());
		mService.deleteManager(manager);
		
		assertEquals(manager.getUsername(), manager2.getUsername());
	}

}
