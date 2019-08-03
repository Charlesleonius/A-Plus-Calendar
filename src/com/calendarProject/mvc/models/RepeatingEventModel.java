package com.calendarProject.mvc.models;

import java.util.ArrayList;

public class RepeatingEventModel extends EventModel {

	private int monthStart;
	private int monthStop;
	private ArrayList<DayName> dayNames;
	
	/**
	 * Setting values.
	 * 
	 * @param name
	 * @param dateData
	 * @param monthStart
	 * @param monthStop
	 * @param dayNames
	 */
	public RepeatingEventModel(String name, DateData dateData, int monthStart, int monthStop,
			ArrayList<DayName> dayNames) {
		
		super(name, dateData);
		this.monthStart = monthStart;
		this.monthStop = monthStop;
		this.dayNames = dayNames;
	
	}

	/**
	 * @return monthStart
	 */
	public int getMonthStart() {
		return monthStart;
	}

	/**
	 * Updating the value for monthStart
	 *
	 * @param monthStart
	 */
	public void setMonthStart(int monthStart) {
		this.monthStart = monthStart;
	}

	/**
	 * @return monthStop
	 */
	public int getMonthStop() {
		return monthStop;
	}

	/**
	 * Updating the value for monthStop
	 *
	 * @param monthStop
	 */
	public void setMonthStop(int monthStop) {
		this.monthStop = monthStop;
	}

	/**
	 * @return dayNames
	 */
	public ArrayList<DayName> getDayNames() {
		return dayNames;
	}

	/**
	 * Updating the value for dayNames
	 *
	 * @param dayNames
	 */
	public void setDayNames(ArrayList<DayName> dayNames) {
		this.dayNames = dayNames;
	}

	/**
	 * Hashcode method.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dayNames == null) ? 0 : dayNames.hashCode());
		result = prime * result + monthStart;
		result = prime * result + monthStop;
		return result;
	}

	/**
	 * Equals method.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof RepeatingEventModel)) {
			return false;
		}
		RepeatingEventModel other = (RepeatingEventModel) obj;
		if (dayNames == null) {
			if (other.dayNames != null) {
				return false;
			}
		} else if (!dayNames.equals(other.dayNames)) {
			return false;
		}
		if (monthStart != other.monthStart) {
			return false;
		}
		if (monthStop != other.monthStop) {
			return false;
		}
		return true;
	}

}
