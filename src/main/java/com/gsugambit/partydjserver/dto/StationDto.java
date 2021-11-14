package com.gsugambit.partydjserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsugambit.partydjserver.model.Station;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.gsugambit.partydjserver.model.QueueItem;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Data
public class StationDto {

	private String name;
	
	public static Station convert(StationDto dto) {
		Station item = new Station();
		item.setName(dto.getName());
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
