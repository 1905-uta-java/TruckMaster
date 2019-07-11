package com.revature.project02.services.serviceImpls;
//imports
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project02.exceptions.BadRequestException;
import com.revature.project02.exceptions.ResourceNotFoundException;
import com.revature.project02.exceptions.UnauthorizedException;
import com.revature.project02.models.Driver;
import com.revature.project02.models.Manager;
import com.revature.project02.models.UnencryptedAuthenticationToken;
import com.revature.project02.models.User;
import com.revature.project02.repositories.ManagerRepository;
import com.revature.project02.repositories.UserRepository;
import com.revature.project02.services.UserService;
import com.revature.project02.util.AuthTokenUtil;
import com.revature.project02.util.HashUtil;
import com.revature.project02.util.ValidationUtil;

@Service
public class UserServiceImpl implements UserService {
	
	//class attributes
	
		@Autowired
		private UserRepository uRepo;
		@Autowired
		private ManagerRepository mRepo;
		
		@Override
		public List<User> getAllUsers(UnencryptedAuthenticationToken uat) {
			
			Optional<User> oUser= uRepo.findById(uat.getUserId());
			if(!oUser.isPresent()) //check if requesting user is an actual user
				throw new UnauthorizedException("Unauthorized user. User does not exist.");
			
			//check if user is an admin
			User user = oUser.get();
			if(AuthTokenUtil.adminAuthenticate(uat, user, ""))
				throw new UnauthorizedException("Unauthorized user.");
			return uRepo.findAll();
		}
		
		@Override
		public User getUserByName(String username, UnencryptedAuthenticationToken uat) {
			Optional<User> oUser= uRepo.findById(uat.getUserId());
			if(!oUser.isPresent()) //check if requesting user is an actual user
				throw new UnauthorizedException("Unauthorized user. User does not exist.");
			
			User user = oUser.get();
			
			if(!AuthTokenUtil.baseAuthenticate(uat, user, "")) //Verify that requesting user is the user that owns the token
				throw new UnauthorizedException("Unauthorized user. User tokens do not match.");
			
			User userReq = uRepo.getUserByUsername(username);
			
			if(!user.equals(userReq) && !this.isPriviledged(uat)) //check if user is asking for themselves
				throw new UnauthorizedException("User request mismatch.");
			
			if ("class com.revature.project02.models.Driver".equals(userReq.getClass().toString())
					&& "class com.revature.project02.models.Manager".equals(uat.getRole()))
				{
					Manager dvrManager = (Manager) uRepo.findById(uat.getUserId()).get();
					Manager testManager = mRepo.getManagerByDrivers((Driver) userReq);
					//if(!mRepo.getManagerByDrivers((Driver) result.get()).equals(mgr))
					if(dvrManager.toString().compareTo(testManager.toString()) != 0) 
					{
						throw new UnauthorizedException("Manager does not have control of this driver.");
					}
				}
			
			return userReq; //success
		}
		
		
		public User getUserByName(String username) {
			
			User userReq = uRepo.getUserByUsername(username);
			
			if(userReq == null)
				throw new ResourceNotFoundException("No such user exists.");
			
			return userReq; //success
		}

		@Override
		public User getUserById(Integer id, UnencryptedAuthenticationToken uat) {
			Optional<User> oUser= uRepo.findById(uat.getUserId());
			if(!oUser.isPresent()) //check if requesting user is an actual user
				throw new UnauthorizedException("Unauthorized user. User does not exist.");
			
			User user = oUser.get();
			
			if(!AuthTokenUtil.baseAuthenticate(uat, user, "")) //Verify that requesting user is the user that owns the token
				throw new UnauthorizedException("Unauthorized user. User tokens do not match.");
			
			Optional<User> result = uRepo.findById(id);
			
			if(!result.isPresent()) //check if the query returns a user
				throw new ResourceNotFoundException("No such user id exists");
			
			
			if(!user.equals(result.get()) && !this.isPriviledged(uat)) //check if requesting user is requesting themself or is an admin/manager
				throw new UnauthorizedException("No priviledge to this user.");
			
			if ("class com.revature.project02.models.Driver".equals(result.get().getClass().toString())
					&& "class com.revature.project02.models.Manager".equals(uat.getRole()))
				{
					Manager dvrManager = (Manager) uRepo.findById(uat.getUserId()).get();
					Manager testManager = mRepo.getManagerByDrivers((Driver) result.get());
					//if(!mRepo.getManagerByDrivers((Driver) result.get()).equals(mgr))
					if(dvrManager.toString().compareTo(testManager.toString()) != 0) 
					{
						throw new UnauthorizedException("Manager does not have control of this driver.");
					}
				}
			
			return (result.get());
		}

		@Override
		public User addUser(User u, UnencryptedAuthenticationToken uat) {
			
			//check if the user id in token matches a user
			if(!this.isTokenVerified(uat))
				throw new UnauthorizedException("User id's do not match."); 
			
			//check if user is an admin or manager
			if(!this.isPriviledged(uat))
				throw new UnauthorizedException("Unauthorized priviledge."); 
			
			if(!ValidationUtil.validUsername(u.getUsername())
					|| !ValidationUtil.validEmail(u.getEmail())
					|| !ValidationUtil.validPhone(u.getPhone())
					|| u.getPassHash().length() != HashUtil.HASH_PASS_EXACT_LEN)
				throw new BadRequestException("Invalid Data.");

			
			return uRepo.save(u);
		}

		@Override
		public User updateUser(User u, UnencryptedAuthenticationToken uat) {
			
			Optional<User> oUser= uRepo.findById(uat.getUserId());
			if(!oUser.isPresent()) //check if requesting user is an actual user
				throw new UnauthorizedException("Unauthorized user. User does not exist.");
			
			User user = oUser.get();
			
			if(!AuthTokenUtil.baseAuthenticate(uat, user, "")) //Verify that requesting user is the user that owns the token
				throw new UnauthorizedException("Unauthorized user. User tokens do not match.");
			
			//Check if the user is instantiated
			if(u == null)
				throw new BadRequestException("Empty user");
			
			//Check if requesting user is themselves or if they are privileged
			if(!user.equals(uRepo.findById(u.getId()).get()) && !this.isPriviledged(uat))
				throw new UnauthorizedException("Unauthorized user. User does not match requested user or underpreviledged.");
			
			if(!ValidationUtil.validEmail(u.getEmail())
				|| !ValidationUtil.validPhone(u.getPhone()))
				throw new BadRequestException("Invalid input.");
			
			if ("class com.revature.project02.models.Driver".equals(u.getClass().toString())
				&& "class com.revature.project02.models.Manager".equals(uat.getRole()))
			{
				Manager mgr = (Manager) uRepo.findById(uat.getUserId()).get();
				if(!mRepo.getManagerByDrivers((Driver) u).equals(mgr)); 
				{
					throw new UnauthorizedException("Manager does not have control of this driver.");
				}
			}
			
			if ("class com.revature.project02.models.Driver".equals(u.getClass().toString())
					&& "class com.revature.project02.models.Manager".equals(uat.getRole()))
				{
					Manager dvrManager = (Manager) uRepo.findById(uat.getUserId()).get();
					Manager testManager = mRepo.getManagerByDrivers((Driver) u);
					//if(!mRepo.getManagerByDrivers((Driver) result.get()).equals(mgr))
					if(dvrManager.toString().compareTo(testManager.toString()) != 0) 
					{
						throw new UnauthorizedException("Manager does not have control of this driver.");
					}
				}
			
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
		public void deleteUser(User u, UnencryptedAuthenticationToken uat) {
			//check if the user id in token matches a user
			if(!this.isTokenVerified(uat))
				throw new UnauthorizedException("User id's do not match."); 
			
			//check if user is an admin or manager
			if(!this.isPriviledged(uat))
				throw new UnauthorizedException("Unauthorized user. User tokens do not match."); 
			
			if(u == null)
				throw new BadRequestException("No such user.");
			uRepo.delete(u);
		}
		
		private boolean isPriviledged(UnencryptedAuthenticationToken uat) {
			Optional<User> oUser= uRepo.findById(uat.getUserId());
			if(!oUser.isPresent()) //check if requesting user is an actual user
				return false;
			
			User user = oUser.get();
			
			if(!AuthTokenUtil.adminAuthenticate(uat, user, "") && !AuthTokenUtil.managerAuthenticate(uat, user, ""))
				return false;
			
			return true;
		}
		
		private boolean isTokenVerified(UnencryptedAuthenticationToken uat) {
			Optional<User> oUser= uRepo.findById(uat.getUserId());
			if(!oUser.isPresent()) //check if requesting user is an actual user
				return false;
			
			User user = oUser.get();
			
			if(!AuthTokenUtil.baseAuthenticate(uat, user, "")) //Verify that requesting user is the user that owns the token
				return false;
			
			return true;
		}
}
