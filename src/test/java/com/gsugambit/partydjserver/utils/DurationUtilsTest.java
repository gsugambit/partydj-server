package com.gsugambit.partydjserver.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DurationUtilsTest {

	@Test
	public void test__convertWithHoursOnly() {
		assertEquals("12:00:00", DurationUtils.convert("PT12H"));
	}
	
	@Test
	public void test__convertWithMinsOnly() {
		assertEquals("56:00", DurationUtils.convert("PT56M"));
	}
	
	@Test
	public void test__convertWithSecsOnly() {
		assertEquals("00:56", DurationUtils.convert("PT56S"));
	}
	
	@Test
	public void test__convertWithHoursMins() {
		assertEquals("12:34:00", DurationUtils.convert("PT12H34M"));
	}
	
	@Test
	public void test__convertWithHoursMinsSecs() {
		assertEquals("12:34:56", DurationUtils.convert("PT12H34M56S"));
	}
	
	@Test
	public void test__convertWithDaysHoursMinsSecs() {
		assertEquals("84:34:56", DurationUtils.convert("P3DT12H34M56S"));
	}
}
