package com.gsugambit.partydjserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class QueueItem {
	
	private String id;
	private long index;
	private String url;
	private boolean played;
	private String user;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public long getIndex() {
		return index;
	}
	
	public void setIndex(long index) {
		this.index = index;
	}
	
	public boolean isPlayed() {
		return played;
	}
	
	public void setPlayed(boolean played) {
		this.played = played;
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
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
