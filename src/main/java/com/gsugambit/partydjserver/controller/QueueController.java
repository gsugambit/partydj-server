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
import com.gsugambit.partydjserver.service.QueueService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/queue")
public class QueueController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueController.class);
	
	private final QueueService queueService;
	
	@Autowired
	public QueueController(final QueueService queueService) {
		this.queueService = queueService;
	}

	@PostMapping("/queue-item")
	public QueueItem addToQueue(@RequestBody QueueItemDto item) {
		return queueService.addQueueItem(item.convert());
	}
	
	@GetMapping("/current-item")
	public QueueItem getCurrentItem() {
		return queueService.getCurrentItem();
	}
	
	@PutMapping("/queue-item/played/{id}")
	public QueueItem played(@PathVariable String id) {
		var queueItem = queueService.played(id);
		LOGGER.info("returning played item: {}", queueItem);
		return queueItem;
	}
	
	@GetMapping("/queue-item")
	public List<QueueItem> retrieveQueue() {
		return queueService.retrieveQueue();
	}
	
	@DeleteMapping("/delete-queue")
	public boolean deleteQueue() {
		queueService.deleteQueue();
		return true;
	}
}
