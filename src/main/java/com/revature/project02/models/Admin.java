package com.revature.project02.models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

<<<<<<< HEAD
=======
import org.springframework.stereotype.Component;

@Component
>>>>>>> f55dcb12ab42d21d2ec79b410a5d4304f7cda0d2
@Entity
@PrimaryKeyJoinColumn(name="USER_ID")
public class Admin extends User {
	public Admin() {
		super();
	}
}
