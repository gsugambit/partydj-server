package com.gsugambit.partydjserver.utils;

import java.time.Duration;

public final class DurationUtils {

	public static String convert(String youtubeDuration) {
		Duration dur = Duration.parse(youtubeDuration);
		String strTime;
		if (dur.toHours() > 0) {
		    strTime = String.format("%d:%02d:%02d", 
		                        dur.toHours(), 
		                        dur.toMinutesPart(), 
		                        dur.toSecondsPart());
		} else {
		    strTime = String.format("%02d:%02d",
		                        dur.toMinutesPart(), 
		                        dur.toSecondsPart());
		}
		return strTime;
	}
}
