import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.project02.TruckMasterRunner;
import com.revature.project02.models.User;
import com.revature.project02.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckMasterRunner.class)
public class UserTest {
	@Autowired
	private UserService user;
	
	@Test
	public void updateUserTest() {
		User u = new User();
		u.setUsername("Apples");
		
		u = user.addUser(u);
		
		u.setUsername("Orange");
		u = user.updateUser(u);
		
		if(u.getId() == null)
			fail();
		User u2 = user.getUserById(u.getId());
		
		assertEquals(u.getUsername(), u2.getUsername());
		
		user.deleteUser(u);
	}
	
	@Test
	public void getUserTest() {
		User u = new User();
		u.setUsername("John");
		u.setPhone("9724447777");
		
		
		user.addUser(u);
		
		assertEquals(u.getUsername(), user.getUserByName("John").getUsername());
		
		user.deleteUser(u);
	}
	
}
