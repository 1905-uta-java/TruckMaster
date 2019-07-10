package com.revature.project02.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.models.Admin;
import com.revature.project02.services.AdminService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/admin")
public class AdminController {
	//Class attributes
	@Autowired
	private AdminService aService;
	
	@GetMapping
	public ResponseEntity teapot(){
		return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
	}

}
