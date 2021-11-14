package com.gsugambit.partydjserver.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsugambit.partydjserver.dao.QueueItemRepository;
import com.gsugambit.partydjserver.dao.StationRepository;
import com.gsugambit.partydjserver.exception.ConstraintException;
import com.gsugambit.partydjserver.exception.NotFoundException;
import com.gsugambit.partydjserver.model.QueueItem;
import com.gsugambit.partydjserver.model.Station;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class QueueItemService {

	private final StationRepository stationRepository;
	private final QueueItemRepository queueItemRespository;

	@Autowired
	public QueueItemService(StationRepository stationRepository, QueueItemRepository queueItemRepository) {
		this.stationRepository = stationRepository;
		this.queueItemRespository = queueItemRepository;
	}

	public Station addQueueItem(String stationId, QueueItem newQueueItem) {
		Station dbStation = validateStation(stationId);
		List<QueueItem> queueItemList = dbStation.getQueue();

		long itemsInQueue = queueItemList.size();
		long existingInQueue = queueItemList.stream()
				.filter(item -> item.getUrl().equals(newQueueItem.getUrl()) && !item.isPlayed()).count();

		if (existingInQueue > 0) {
			throw new RuntimeException("url already in queue and unplayed: " + newQueueItem.getUrl());
		}

		newQueueItem.setIndex(itemsInQueue + 1);
		newQueueItem.setPlayed(false);
		newQueueItem.setStation(dbStation);

		queueItemList.add(newQueueItem);

		LOGGER.info("User added to item: {} with index: {} to queue for station: {}.", newQueueItem.getUrl(),
				newQueueItem.getIndex(), stationId);

		stationRepository.save(dbStation);
		return dbStation;
	}

	public QueueItem getCurrentItem(String stationId) {
		Station dbStation = validateStation(stationId);
		
		return dbStation.getQueue()
				.stream()
				.filter(item -> !item.isPlayed()).findFirst().orElse(null);
	}

	public Station deleteQueue(String stationId) {
		Station dbStation = validateStation(stationId);
		
		dbStation.getQueue().clear();
		stationRepository.save(dbStation);
		return dbStation;
	}

	public QueueItem played(String stationId, String songId) {
		LOGGER.info("Client is suggesting that stationId: {} songId: {} has finished playing.", stationId, songId);
		
		Station dbStation = validateStation(stationId);
		Optional<QueueItem> queueItemOp = 
				dbStation.getQueue()
				.stream()
				.filter(item -> item.getId().equals(songId))
				.findFirst();
		
		
		
		if (!queueItemOp.isPresent()) {
			throw new ConstraintException("song: " + songId + " does not exist inside queue");
		} 
		
		var queueItem = queueItemOp.get();
		queueItem.setPlayed(true);
		queueItemRespository.save(queueItem);
		return queueItem;
	}

	public List<QueueItem> retrieveQueue(String stationId) {
		Station dbStation = validateStation(stationId);
		return dbStation.getQueue();
	}
	
	protected Station validateStation(String stationId) {
		Optional<Station> opStation = stationRepository.findById(stationId);

		if (opStation.isEmpty()) {
			throw new NotFoundException("station: " + stationId + " does not exist.");
		}

		return opStation.get();
	}

}
