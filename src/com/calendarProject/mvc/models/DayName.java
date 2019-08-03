/*
 * Authored By: Yongen Chen
 */
package com.calendarProject.mvc.models;

import java.util.ArrayList;

public enum DayName {

	SUNDAY {
		@Override
		public String toString() {
			return "S";
		}
	},
	MONDAY{
		@Override
		public String toString() {
			return "M";
		}
	},
	TUESDAY{
		@Override
		public String toString() {
			return "T";
		}
	},
	WEDNESDAY{
		@Override
		public String toString() {
			return "W";
		}
	},
	THURSDAY{
		@Override
		public String toString() {
			return "H";
		}
	},
	FRIDAY{
		@Override
		public String toString() {
			return "F";
		}
	},
	SATURNDAY{
		@Override
		public String toString() {
			return "A";
		}
	};
	
	public static ArrayList<DayName> getAllDays(){
		
		ArrayList<DayName> list = new ArrayList<>();
		list.add(DayName.SUNDAY);
		list.add(DayName.MONDAY);
		list.add(DayName.TUESDAY);
		list.add(DayName.WEDNESDAY);
		list.add(DayName.THURSDAY);
		list.add(DayName.FRIDAY);
		list.add(DayName.SATURNDAY);
		
		return list;
		
	}
	
	public static ArrayList<DayName> getDayNames(String days) {
		
		ArrayList<DayName> list = new ArrayList<>();
	
		char[] daysArray = days.toCharArray();
		for(char day: daysArray) {
			
			switch(day) {
			case 'S':
				list.add(DayName.SUNDAY);
				break;
			case 'M':
				list.add(DayName.MONDAY);
				break;
			case 'T':
				list.add(DayName.TUESDAY);
				break;
			case 'W':
				list.add(DayName.WEDNESDAY);
				break;
			case 'H':
				list.add(DayName.THURSDAY);
				break;
			case 'F':
				list.add(DayName.FRIDAY);
				break;
			case 'A':
				list.add(DayName.SATURNDAY);
				break;
			}
			
		}
		
		return list;
		
	}
	
}
