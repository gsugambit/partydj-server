package com.gsugambit.partydjserver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import com.gsugambit.partydjserver.model.Station;
import com.gsugambit.partydjserver.utils.CloneUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.gsugambit.partydjserver.model.QueueItem;

@Service
public class QueueItemService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueItemService.class);
	
	private static final List<Station> STATION_LIST = new ArrayList<>();
	private static final Map<String, List<QueueItem>> STATION_QUEUE_LIST = new HashMap<>();
	
	public Station addQueueItem(String stationId, QueueItem newQueueItem) {
		
		Optional<Station> opStation = STATION_LIST.stream()
				.filter(station -> station.getId().equals(stationId))
				.findFirst();
		
		if(opStation.isEmpty()) {
			throw new RuntimeException("station: " 
					+ stationId +  " does not exist." );
		}
		
		if(!STATION_QUEUE_LIST.containsKey(stationId)) {
			throw new RuntimeException("queue for station: " 
					+ stationId +  " does not exist" );
		}
		
		Station station = opStation.get();
		List<QueueItem> queueItemList = STATION_QUEUE_LIST.get(stationId);
		
		long itemsInQueue = queueItemList.size();
		long existingInQueue = queueItemList.stream()
				.filter(item -> item.getUrl().equals(newQueueItem.getUrl()) && !item.isPlayed())
				.count();
		
		if(existingInQueue > 0) {
			throw new RuntimeException("url already in queue and unplayed: " + newQueueItem.getUrl());
		}
		
		newQueueItem.setIndex(itemsInQueue + 1);
		newQueueItem.setId(UUID.randomUUID().toString());
		
		queueItemList.add(newQueueItem);
		
		LOGGER.info("User added to item: {} with index: {} to queue for station: {}."
				, newQueueItem.getUrl(), newQueueItem.getIndex(), stationId);
		
		Station returnStation = CloneUtils.clone(station, new TypeReference<Station>() {
		});
		
		return returnStation;		
	}

	public QueueItem getCurrentItem(String stationId) {
		if(STATION_QUEUE_LIST.containsKey(stationId)) {
			List<QueueItem> queueItemList = STATION_QUEUE_LIST.get(stationId);
		
		
			if(queueItemList.isEmpty()) {
				return null;
			}
		
			return
					queueItemList.stream().filter(item -> !item.isPlayed()).findFirst()
					.orElse(null);
		}
		
		return null;
	}

	public Station deleteQueue(String stationId) {
		Optional<Station> stationOp =
				STATION_LIST.stream()
				.filter(station -> station.getId().equals(stationId)).findFirst();
		
		if(stationOp.isEmpty()) {
			throw new RuntimeException("cannot find station with id: " + stationId);
		}
		
		Station station = stationOp.get();
		
		if(STATION_QUEUE_LIST.containsKey(stationId)) {
			List<QueueItem> queueItemList = STATION_QUEUE_LIST.get(stationId);
			LOGGER.info("Clearing the queue for station: {}", stationId);
			queueItemList.clear();
		}
		
		return station;
	}

	public QueueItem played(String stationId, String songId) {
		LOGGER.info("Client is suggesting that stationId: {} songId: {} has finished playing.", stationId, songId);
		if(STATION_QUEUE_LIST.containsKey(stationId)) {
			List<QueueItem> queueItemList = STATION_QUEUE_LIST.get(stationId);
			
			Optional<QueueItem> queueItemOp = queueItemList.stream().filter(item -> item.getId().equals(songId)).findFirst();
			
			if(queueItemOp.isPresent()) {
				var queueItem = queueItemOp.get();
				queueItem.setPlayed(true);
				return queueItem;
			}
		}
		return null;
	}

	public List<QueueItem> retrieveQueue(String stationId) {
		if(STATION_QUEUE_LIST.containsKey(stationId)) {
			return STATION_QUEUE_LIST.get(stationId);
		}
		return new ArrayList<>();
	}

	public Station create(Station newStation) {
		LOGGER.info("User is trying to create station: ", newStation);
		final long stationCount = 
				STATION_LIST.stream().filter(station -> station.getName().equals(newStation.getName())).count();
		
		if(stationCount > 0) {
			throw new RuntimeException("station already exists with name: " + newStation.getName());
		}
		
		if(!StringUtils.hasText(newStation.getName())) {
			throw new RuntimeException("cannot create a station with no name");
		}
		
		newStation.setId(UUID.randomUUID().toString());
		STATION_LIST.add(newStation);
		STATION_QUEUE_LIST.put(newStation.getId(), new ArrayList<>());
		
		LOGGER.info("User created station: ", newStation);
		
		return newStation;
	}

	public List<Station> getAllStations() {
		return STATION_LIST;
	}

	public Station getStation(String stationId) {
		Optional<Station> stationOp =
				STATION_LIST.stream()
				.filter(station -> station.getId().equals(stationId)).findFirst();
		
		if(stationOp.isEmpty()) {
			throw new RuntimeException("cannot find station with id: " + stationId);
		}
		
		Station station = stationOp.get();
		List<QueueItem> queueItems = STATION_QUEUE_LIST.get(stationId);
		queueItems.forEach(item -> item.setStationId(stationId));
		
		Station returnStation = CloneUtils.clone(station, new TypeReference<Station>() {
		});
		
		returnStation.setQueue(queueItems);
		return returnStation;
	}
}
