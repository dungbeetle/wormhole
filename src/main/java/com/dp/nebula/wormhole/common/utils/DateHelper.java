package com.dp.nebula.wormhole.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DateHelper {
    private static final String DATE_PATTERN = "HH:mm:ss.SSS";
    public static final String DATE_FORMAT_PATTERN_YEAR_MONTH_DAY = "yyyy-mm-dd HH:mm:ss";

		private static BlockingQueue<SimpleDateFormat> m_queue = new ArrayBlockingQueue<SimpleDateFormat>(20);

		public static String format(Date date) {
			SimpleDateFormat format = m_queue.poll();

			if (format == null) {
				format = new SimpleDateFormat(DATE_FORMAT_PATTERN_YEAR_MONTH_DAY);
				format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			}

			try {
				return format.format(date);
			} finally {
				if (m_queue.remainingCapacity() > 0) {
					m_queue.offer(format);
				}
			}
		}

		public static Date parse(String str) {
			SimpleDateFormat format = m_queue.poll();

			if (format == null) {
				format = new SimpleDateFormat(DATE_FORMAT_PATTERN_YEAR_MONTH_DAY);
				format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			}

			try {
				return format.parse(str);
			} catch (ParseException e) {
				return null;
			} finally {
				if (m_queue.remainingCapacity() > 0) {
					m_queue.offer(format);
				}
			}
		}
	}

