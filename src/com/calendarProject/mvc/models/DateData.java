/*
 * Authored By: Cong Wang
 */
package com.calendarProject.mvc.models;

import java.util.Calendar;

public class DateData {

	private int day;
	private int month;
	private int year;
	private int hourStart;
	private int hourStop;
	
	/**
	 * Short Constructor.
	 * 
	 * @param day
	 * @param month
	 * @param year
	 */
	public DateData(int day, int month, int year) {
	
		this.day = day;
		this.month = month;
		this.year = year;
		this.hourStart = 0;
		this.hourStop = 0;
		
	}

	/**
	 * Setting Values.
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @param hourStart
	 * @param hourStop
	 */
	public DateData(int day, int month, int year, int hourStart, int hourStop) {
	
		this.day = day;
		this.month = month;
		this.year = year;
		this.hourStart = hourStart;
		this.hourStop = hourStop;
	
	}

	/**
	 * @return day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Updating the value for day
	 *
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Updating the value for month
	 *
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Updating the value for year
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return hourStart
	 */
	public int getHourStart() {
		return hourStart;
	}

	/**
	 * Updating the value for hourStart
	 *
	 * @param hourStart
	 */
	public void setHourStart(int hourStart) {
		this.hourStart = hourStart;
	}

	/**
	 * @return hourStop
	 */
	public int getHourStop() {
		return hourStop;
	}

	/**
	 * Updating the value for hourStop
	 *
	 * @param hourStop
	 */
	public void setHourStop(int hourStop) {
		this.hourStop = hourStop;
	}

	/**
	 * Hashcode method.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + hourStart;
		result = prime * result + hourStop;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	/**
	 * Equals method to compare 2 objects.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DateData)) {
			return false;
		}
		DateData other = (DateData) obj;
		if (day != other.day) {
			return false;
		}
		if (hourStart != other.hourStart) {
			return false;
		}
		if (hourStop != other.hourStop) {
			return false;
		}
		if (month != other.month) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}
	
	/**
	 * Only day, month, year testing 2 objects.
	 */
	public boolean calendarEquals(DateData other) {
		
		if (day != other.day) {
			return false;
		}
		if (month != other.month) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	
	}
		
	public static boolean calendarEquals(Calendar first, Calendar second) {
		
		return (first.get(Calendar.DAY_OF_MONTH) == second.get(Calendar.DAY_OF_MONTH))
				&& (first.get(Calendar.MONTH) == second.get(Calendar.MONTH))
				&& (first.get(Calendar.YEAR) == second.get(Calendar.YEAR));
		
	}
	
	public String toString() {
		
		return (month+1)+"/"+day+"/"+year+" T: "+hourStart+":00"+"-"+hourStop+":00";
		
	}
	
	
}
