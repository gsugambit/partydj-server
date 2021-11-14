package com.gsugambit.partydjserver.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsugambit.partydjserver.model.Station;

public interface StationRepository extends PagingAndSortingRepository<Station, String>{

	Optional<Station> findByUrl(String url);

	Optional<Station> findByName(String name);
}
