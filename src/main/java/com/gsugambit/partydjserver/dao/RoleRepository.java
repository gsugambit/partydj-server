package com.gsugambit.partydjserver.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsugambit.partydjserver.model.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, String>{
	
	Optional<Role> findByName(String name);

}
