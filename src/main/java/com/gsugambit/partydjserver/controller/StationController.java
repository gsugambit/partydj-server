package com.gsugambit.partydjserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsugambit.partydjserver.dto.StationDto;
import com.gsugambit.partydjserver.model.Station;
import com.gsugambit.partydjserver.service.QueueItemService;
import com.gsugambit.partydjserver.service.StationService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/station")
public class StationController {

	private StationService stationService;
	
	@Autowired
	public StationController(final StationService stationService) {
		this.stationService = stationService;
	}
	
	@PostMapping()
	public Station create(@RequestBody StationDto stationDto) {
		return stationService.create(stationDto);
	}
	
	@GetMapping
	public List<Station> get() {
		return stationService.getAllStations();
	}
	
	@GetMapping("/url/{url}")
	public Station getByUrl(@PathVariable("url") String url) {
		return stationService.getStationByUrl(url);
	}
	
	@GetMapping("/{stationId}")
	public Station getStation(@PathVariable String stationId) {
		return stationService.getStation(stationId);
	}
	
	@DeleteMapping("/{stationId}")
	public boolean deleteStation(@PathVariable String stationId) {
		stationService.deleteStation(stationId);
		return true;
	}
}
