package com.revature.project02.repositories;
import java.util.List;

//imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project02.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	public List<Admin> getAllAdminsById(Integer id);
	public List<Admin> getAllAdminsByUsername(String username);
	public Admin getAdminById(Integer id);
	public Admin getAdminByUsername(String username);
}
