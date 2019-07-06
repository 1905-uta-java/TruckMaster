package com.revature.project02.services.serviceImpls;
//imports
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.project02.models.User;
import com.revature.project02.repositories.UserRepository;
import com.revature.project02.services.UserService;

public class UserServiceImpl implements UserService {
	
	//class attributes
	
		@Autowired
		private UserRepository uRepo;
		
		@Override
		public List<User> getAllUsers() {
			return uRepo.findAll();
		}
		
		@Override
		public User getUserByName(String username) {
			List<User> userList = uRepo.getAllUserByUsername(username);
			
			if(userList.size() == 1)
				return userList.get(0);
			
			return null;
		}

		@Override
		public User getUserById(Integer id) {
			return uRepo.getOne(id);
		}

		@Override
		public User addUser(User u) {
			return uRepo.save(u);
		}

		@Override
		public User updateUser(User u) {
			return uRepo.save(u);
		}

		@Override
		public boolean deleteUser(User u) {
			try {
				uRepo.delete(u);
				return true;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
}
