package com.example.demomcq.service;

import com.example.demomcq.model.User;
import com.example.demomcq.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	

	@Autowired
	UserRepo userRepo;
	
	public User findById(String email) {
		return userRepo.findByEmail(email);
	}
}
