package com.gsugambit.partydjserver.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.gsugambit.partydjserver.dao.StationRepository;
import com.gsugambit.partydjserver.dto.StationDto;
import com.gsugambit.partydjserver.exception.ConstraintException;
import com.gsugambit.partydjserver.exception.InvalidDomainException;
import com.gsugambit.partydjserver.exception.NotFoundException;
import com.gsugambit.partydjserver.model.Station;
import com.gsugambit.partydjserver.utils.UUIDGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class StationService {
	
	private final StationRepository stationRepository;
	
	@Autowired
	public StationService(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}
	
	public Station create(StationDto newStation) {
		LOGGER.info("User is trying to create station: {}", newStation);
		
		Optional<Station> stationOp = stationRepository.findByName(newStation.getName());
		
		if(stationOp.isPresent()) {
			throw new ConstraintException("station already exists with name: " + newStation.getName());
		}
		
		if(!StringUtils.hasText(newStation.getName())) {
			throw new InvalidDomainException("cannot create a station with no name");
		}
		
		Station stationToCreate = StationDto.convert(newStation);
		stationToCreate.setUrl(UUIDGenerator.generate());
		
		stationRepository.save(stationToCreate);
		LOGGER.info("User created station: {}", stationToCreate);
		return stationToCreate;
	}

	public List<Station> getAllStations() {
		return Lists.newArrayList(stationRepository.findAll());
	}

	public Station getStation(String stationId) {
		Optional<Station> stationOp = stationRepository.findById(stationId);
	
		return stationOp
				.orElseThrow(() -> new RuntimeException("cannot find station with id: " + stationId));
	}
	
	public Station getStationByUrl(String url) {
		Optional<Station> stationOp = stationRepository.findByUrl(url);
		
		return stationOp
				.orElseThrow(() -> new NotFoundException("cannot find station with url: " + url));
	}

	public void deleteStation(String stationId) {
		Optional<Station> stationOp = stationRepository.findById(stationId);
		
		if(stationOp.isEmpty()) {
			throw new ConstraintException("cannot find station with id: " + stationId);
		}
		
		stationRepository.deleteById(stationId);
	}
}
