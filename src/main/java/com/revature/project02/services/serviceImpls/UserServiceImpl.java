package com.revature.project02.services.serviceImpls;
//imports
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.exceptions.ResourceNotFoundException;
import com.revature.project02.models.User;
import com.revature.project02.repositories.UserRepository;
import com.revature.project02.services.UserService;

@Service
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
			//return uRepo.getOne(id);
			Optional<User> result = uRepo.findById(id);
			
			return (result.isPresent()) ? result.get():null;
		}

		@Override
		public User addUser(User u) {
			//if(uRepo.existsById(u.getId())) //if the user exists then do not add
				//return null;
			
			return uRepo.save(u);
		}

		@Override
		public User updateUser(User u) {
			//if the user exists then update the user
			if(u == null)
				throw new BadRequestException("Empty user");
			Optional<User> u2 = uRepo.findById(u.getId());
			if(!u2.isPresent())
				throw new ResourceNotFoundException("No such user");
			
			User u3 = u2.get();
			u3.setEmail(u.getEmail());
			u3.setPhone(u.getPhone());
			return uRepo.save(u3);
			
			//return null;
		}

		@Override
		public void deleteUser(User u) {
			if(u == null)
				throw new BadRequestException("No such user.");
			uRepo.delete(u);
		}
}
