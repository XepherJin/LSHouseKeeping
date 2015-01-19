package com.retropoktan.lshousekeeping.net;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String getTimeStampFromString(String timeString) {
		String timestamp = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = simpleDateFormat.parse(timeString);
			long l = date.getTime();
			String str = String.valueOf(l);
			timestamp = str.substring(0, 10);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return timestamp;
	}
}
