/*
 * Authored By: Cong Wang
 */
package com.calendarProject.mvc.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import com.calendarProject.mvc.models.DateData;
import com.calendarProject.mvc.models.DayName;
import com.calendarProject.mvc.models.RepeatingEventModel;

public class FileExecutor {

	/**
	 * Get repeating events..
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<RepeatingEventModel> getRepeatingEvents(String filename) throws Exception {
		ArrayList<RepeatingEventModel> events = new ArrayList<>();
		BufferedReader stream = new BufferedReader(new FileReader(new File(filename)));
		String event = "";
		while((event = stream.readLine()) != null) {
			String[] values = event.split(";");
			String nameOfEvent = values[0];
			int year = Integer.parseInt(values[1]);
			int startMonth = Integer.parseInt(values[2]);
			int monthStop = Integer.parseInt(values[3]);
			String allDays = values[4];
			int hourStart = Integer.parseInt(values[5]);
			int hourEnd = Integer.parseInt(values[6]);
			events.add(new RepeatingEventModel(nameOfEvent, 
					new DateData(1, startMonth - 1, year, hourStart, hourEnd), 
					startMonth - 1, monthStop, DayName.getDayNames(allDays)));
		}
		stream.close();
		return events;
	}

}
