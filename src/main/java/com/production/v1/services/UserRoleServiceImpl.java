package com.production.v1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.production.v1.model.UserRole;
import com.production.v1.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleRepository roleRepo; 
	@Override
	public List<UserRole> getAllRoles() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}

}
