package com.gsugambit.partydjserver.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsugambit.partydjserver.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, String>{
	
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);

}
