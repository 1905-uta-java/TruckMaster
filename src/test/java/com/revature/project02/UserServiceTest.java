package com.revature.project02;
import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.project02.TruckMasterRunner;
import com.revature.project02.controllers.AuthTokenController;
import com.revature.project02.models.Manager;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;
import com.revature.project02.services.UserService;
import com.revature.project02.services.serviceImpls.UserServiceImpl;
import com.revature.project02.util.AuthTokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckMasterRunner.class)
public class UserServiceTest {
	@Autowired
	private UserService user;
	
	
	@Test
	public void updateUserTest() {
		User man = new Manager();
		man.setId(5202);
		man.setUsername("manager_poc2");
		
		String ip = "127.0.0.1";
		Long curtime = System.currentTimeMillis();
		System.out.println("stuff: " + curtime + man.getId() + man.getUsername());
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(
				man.getId(), man.getUsername(), man.getClass().toString(), ip, curtime);
		
		User u = new User();
		u.setUsername("Apples");
		
		u = user.addUser(u, uat);
		
		u.setUsername("Orange");
		u = user.updateUser(u, uat);
		
		if(u.getId() == null)
			fail();
		User u2 = user.getUserById(u.getId(), uat);
		
		assertEquals(u.getUsername(), u2.getUsername());
		
		user.deleteUser(u, uat);
	}
	
	
	@Test
	public void getUserTest() {
		
		User man = new Manager();
		man.setId(5202);
		man.setUsername("manager_poc2");
		
		String ip = "127.0.0.1";
		Long curtime = System.currentTimeMillis();
		System.out.println("stuff: " + curtime + man.getId() + man.getUsername());
		UnencryptedAuthenticationToken uat = new UnencryptedAuthenticationToken(
				man.getId(), man.getUsername(), man.getClass().toString(), ip, curtime);
		
		assertNotNull(user.getUserById(5302, uat));
		
	}
	
}
