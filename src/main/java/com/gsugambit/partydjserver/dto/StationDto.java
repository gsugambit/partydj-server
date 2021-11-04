package com.gsugambit.partydjserver.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsugambit.partydjserver.model.Station;
import com.gsugambit.partydjserver.model.QueueItem;

public class StationDto {

	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
	
	public Station convert() {
		Station item = new Station();
		item.setName(this.name);
		
		return item;
	}
}
