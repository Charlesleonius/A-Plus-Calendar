package com.calendarProject.mvc.controllers;

import java.util.Calendar;

public class GregorianCalendar {

	private Calendar calendar;
	
	public GregorianCalendar() {
		this.calendar = Calendar.getInstance();
	}
	
	public Calendar nextDay() {
		this.calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}
	
	public Calendar previousDay() {
		this.calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar;
	}
	
	public Calendar nextMonth() {
		this.calendar.add(Calendar.MONTH, 1);
		this.calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}
	
	public Calendar previousMonth() {
		this.calendar.add(Calendar.MONTH, -1);
		this.calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	public Calendar getTodayCalendar() {
		this.calendar = Calendar.getInstance();
		return this.calendar;
	}
	
	public Calendar getCalendarForDay(int day) {
		this.calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar;
	}
	
	public int day() {
		return this.calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public int month() {
		return this.calendar.get(Calendar.MONTH);
	}
	
	public int year() {
		return this.calendar.get(Calendar.YEAR);
	}
	
	public int weekOfMonth() {
		return this.calendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	public int currentDayNumber() {
		return this.calendar.get(Calendar.DAY_OF_MONTH);
	}

}
