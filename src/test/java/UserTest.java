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
		u.setId(1);
		u.setUsername("HelloWorld");
		
		user.addUser(u);
		
		u.setUsername("GoodbyeWorld");
		user.updateUser(u);
		
		
		assertTrue(user.getUserById(u.getId()).toString().equals(u.toString()));
	}
	
}
