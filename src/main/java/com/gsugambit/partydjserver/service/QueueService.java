package com.gsugambit.partydjserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsugambit.partydjserver.model.QueueItem;

@Service
public class QueueService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);
	
	private static final List<QueueItem> QUEUE_LIST = new ArrayList<>();
	
	public QueueItem addQueueItem(QueueItem newQueueItem) {
		long itemsInQueue = QUEUE_LIST.size();
		long existingInQueue = QUEUE_LIST.stream()
				.filter(item -> item.getUrl().equals(newQueueItem.getUrl()) && !item.isPlayed())
				.count();
		
		if(existingInQueue > 0) {
			throw new RuntimeException("url already in queue and unplayed: " + newQueueItem.getUrl());
		}
		
		newQueueItem.setIndex(itemsInQueue + 1);
		newQueueItem.setId(UUID.randomUUID().toString());
		
		QUEUE_LIST.add(newQueueItem);
		
		LOGGER.info("User added to item: {} with index: {} to queue.", newQueueItem.getUrl(), newQueueItem.getIndex());
		
		return newQueueItem;		
	}

	public QueueItem getCurrentItem() {
		if(QUEUE_LIST.isEmpty()) {
			return null;
		}
		
		return
				QUEUE_LIST.stream().filter(item -> !item.isPlayed()).findFirst()
				.orElse(null);
	}

	public void deleteQueue() {
		LOGGER.info("Clearing the queue");
		QUEUE_LIST.clear();
	}

	public QueueItem played(String id) {
		LOGGER.info("Client is suggesting that {} has finished playing.", id);
		Optional<QueueItem> queueItemOp = QUEUE_LIST.stream().filter(item -> item.getId().equals(id)).findFirst();
		
		if(queueItemOp.isPresent()) {
			var queueItem = queueItemOp.get();
			queueItem.setPlayed(true);
			return queueItem;
		}
		
		return null;
	}

	public List<QueueItem> retrieveQueue() {
		return QUEUE_LIST;
	}
}
