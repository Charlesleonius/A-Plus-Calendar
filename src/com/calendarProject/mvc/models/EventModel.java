package com.calendarProject.mvc.models;

public class EventModel {

	private String name;
	private DateData dateData;
	
	/**
	 * Setting Event Model.
	 * 
	 * @param name
	 * @param dateData
	 */
	public EventModel(String name, DateData dateData) {
	
		this.name = name;
		this.dateData = dateData;
	
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Updating the value for name
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return dateData
	 */
	public DateData getDateData() {
		return dateData;
	}

	/**
	 * Updating the value for dateData
	 *
	 * @param dateData
	 */
	public void setDateData(DateData dateData) {
		this.dateData = dateData;
	}

	/**
	 * HashCode...
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateData == null) ? 0 : dateData.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Equals Method.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EventModel)) {
			return false;
		}
		EventModel other = (EventModel) obj;
		if (dateData == null) {
			if (other.dateData != null) {
				return false;
			}
		} else if (!dateData.equals(other.dateData)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public boolean similar(EventModel eventModel) {
		
		DateData currentDateData = eventModel.getDateData();
		
		return eventModel.getDateData().calendarEquals(this.dateData) && 
				((dateData.getHourStart() >= currentDateData.getHourStart() && 
				dateData.getHourStart() < currentDateData.getHourStop()) ||
				(dateData.getHourStop() > currentDateData.getHourStart() && 
				dateData.getHourStop() <= currentDateData.getHourStop()) ||
				(currentDateData.getHourStart() >= dateData.getHourStart() && 
				currentDateData.getHourStart() < dateData.getHourStop()) ||
				(currentDateData.getHourStop() > dateData.getHourStart() && 
				currentDateData.getHourStop() <= dateData.getHourStop()));
		
	}
	
}
