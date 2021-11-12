package com.gsugambit.partydjserver.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsugambit.partydjserver.model.QueueItem;

public class QueueItemDto {

	private String stationId;
	private String title;
	private String url;
	private String user;
	
	public String getStation() {
		return stationId;
	}
	
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public QueueItem convert() {
		QueueItem item = new QueueItem();
		item.setUrl(this.url);
		item.setUser(this.user);
		item.setTitle(this.title);
		
		return item;
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
