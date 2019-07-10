package com.revature.project02.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project02.models.Manager;
import com.revature.project02.services.ManagerService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private ManagerService mService;
	
	@PostMapping
	public ResponseEntity<Manager> addManager(@RequestBody Manager manager){
		return new ResponseEntity<>(mService.addManager(manager), HttpStatus.CREATED);
	}
	
}
