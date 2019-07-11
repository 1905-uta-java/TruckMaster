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
import com.revature.project02.util.HashUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckMasterRunner.class)
public class ManagerServiceTest {
	
	@Autowired
	private ManagerService mService;
	
	@Test
	public void getManagerByUsername() {
		Manager manager = new Manager();
		manager.setUsername("johnnyus");
		manager.setId(2505);
		manager.setEmail("validh@email.com");
		manager.setPassHash(HashUtil.hashStr("Password1"));
		manager.setPhone("(702) 358-1983");

		
		manager = mService.addManager(manager, "Password1");
		
		Manager manager2 = mService.getManagerByUsername("johnnyus");
		
		mService.deleteManager(manager2);
		
		assertEquals(manager.getId(), manager2.getId());
		
	}
	
	@Test
	public void getManagerById() {
		
		Manager manager = new Manager();
		
		manager.setUsername("johnnay");
		manager.setId(2506);
		manager.setEmail("validi@email.com");
		manager.setPassHash(HashUtil.hashStr("validPassword"));
		manager.setPhone("(702) 358-2295");
		
		Manager manager2 = mService.addManager(manager, "Password1");
		manager2 = mService.getManagerById(manager2.getId());
		mService.deleteManager(manager2);
		
		assertEquals(manager.getUsername(), manager2.getUsername());
	}

}
