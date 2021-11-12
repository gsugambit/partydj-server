package com.gsugambit.partydjserver.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DurationUtilsTest {

	@Test
	public void test__convertWithHoursOnly() {
		assertEquals("12", DurationUtils.convert("PT12H"));
	}
}
