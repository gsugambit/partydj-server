package com.gsugambit.partydjserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsugambit.partydjserver.dto.StationDto;
import com.gsugambit.partydjserver.model.Station;
import com.gsugambit.partydjserver.service.QueueItemService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/station")
public class StationController {

	private QueueItemService queueService;
	
	@Autowired
	public StationController(final QueueItemService queueService) {
		this.queueService = queueService;
	}
	
	@PostMapping()
	public Station create(@RequestBody StationDto stationDto) {
		return queueService.create(stationDto.convert());
	}
	
	@GetMapping
	public List<Station> get() {
		return queueService.getAllStations();
	}
	
	@GetMapping("/{stationId}")
	public Station getStation(@PathVariable String stationId) {
		return queueService.getStation(stationId);
	}
}
