package com.gsugambit.partydjserver.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Station {

	private String id;
	private String name;
	private List<QueueItem> queue = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	
	public void setId(String stationId) {
		this.id = stationId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<QueueItem> getQueue() {
		return queue;
	}
	
	public void setQueue(List<QueueItem> queue) {
		this.queue = queue;
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
