package com.gsugambit.partydjserver.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsugambit.partydjserver.dto.QueueItemDto;
import com.gsugambit.partydjserver.model.QueueItem;
import com.gsugambit.partydjserver.model.Station;
import com.gsugambit.partydjserver.service.QueueItemService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/queue")
public class QueueItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueItemController.class);
	
	private final QueueItemService queueService;
	
	@Autowired
	public QueueItemController(final QueueItemService queueService) {
		this.queueService = queueService;
	}

	@PostMapping("{stationId}/queue-item")
	public Station addToQueue(@PathVariable String stationId, @RequestBody QueueItemDto item) {
		return queueService.addQueueItem(stationId, item.convert());
	}
	
	@GetMapping("{stationId}/current-item")
	public QueueItem getCurrentItem(@PathVariable String stationId) {
		return queueService.getCurrentItem(stationId);
	}
	
	@PutMapping("{stationId}/queue-item/played/{id}")
	public QueueItem played(@PathVariable String stationId, @PathVariable String id) {
		var queueItem = queueService.played(stationId, id);
		LOGGER.info("returning played item: {}", queueItem);
		return queueItem;
	}
	
	@GetMapping("{stationId}/queue-item")
	public List<QueueItem> retrieveQueue(@PathVariable String stationId) {
		return queueService.retrieveQueue(stationId);
	}
	
	@DeleteMapping("{stationId}/delete-queue")
	public Station deleteQueue(@PathVariable String stationId) {
		return queueService.deleteQueue(stationId);
	}
}
