package com.gsugambit.partydjserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CloneUtils {

	public static <T> T clone(T t, TypeReference<T> typeReference) {
		try {
			return new ObjectMapper()
					.readValue(new ObjectMapper().writeValueAsString(t), typeReference);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("could not deep copy object: " + e.getMessage(), e);
		}
	}
}
