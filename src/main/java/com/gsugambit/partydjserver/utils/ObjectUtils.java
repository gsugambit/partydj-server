package com.gsugambit.partydjserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectUtils {

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return object.toString();
		}
	}
	
	private ObjectUtils() {
		
	}
}
