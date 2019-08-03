package com.calendarProject.mvc.controllers;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import com.calendarProject.mvc.models.DateData;
import com.calendarProject.mvc.models.DayName;
import com.calendarProject.mvc.models.EventModel;
import com.calendarProject.mvc.models.RepeatingEventModel;
import com.calendarProject.mvc.views.CalendarEventView;

public class CalendarEventController {

	public final static int EVENTS_FOR_DAY = 1;
	public final static int EVENTS_FOR_MONTH = 2;
	public final static int EVENTS_FOR_WEEK = 3;
	
	// Attributes..
	private ArrayList<EventModel> calendarEvents;
	private GregorianCalendar gregorianCalendar;
	
	public CalendarEventController() {
		
		this.gregorianCalendar = new GregorianCalendar();
		this.calendarEvents = new ArrayList<>();
		new CalendarEventView(this);
		
	}

	/**
	 * @return gregorianCalendar
	 */
	public GregorianCalendar getGregorianCalendar() {

		return gregorianCalendar;
	
	}
	
	public boolean addEventModel(EventModel eventModel) {
		
		System.out.println(eventModel.getName()+", "+eventModel.getDateData());
		for(int i = 0; i < calendarEvents.size(); i++) {
			
			if(this.calendarEvents.get(i).similar(eventModel)) {
				return false;
			}
			
		}
		this.calendarEvents.add(eventModel);
		return true;
		
	}
	
	public String getEventDetails(int eventsFor) {
		
		String text = "";
		
		for(EventModel model: calendarEvents) {
			
			Calendar calendarForWeek = Calendar.getInstance();
			calendarForWeek.set(Calendar.DAY_OF_MONTH, model.getDateData().getDay());
			calendarForWeek.set(Calendar.MONTH, model.getDateData().getMonth());
			calendarForWeek.set(Calendar.YEAR, model.getDateData().getYear());
			
			if(model.getDateData().getYear() == this.gregorianCalendar.year()) {
				
				if((eventsFor == EVENTS_FOR_DAY && model.getDateData().getDay() == this.gregorianCalendar.day() &&
						model.getDateData().getMonth() == this.gregorianCalendar.month()) ||
						(eventsFor == EVENTS_FOR_MONTH && model.getDateData().getMonth() == this.gregorianCalendar.month()) ||
						(eventsFor == EVENTS_FOR_WEEK && calendarForWeek.get(Calendar.WEEK_OF_MONTH) == this.gregorianCalendar.weekOfMonth()  &&
								model.getDateData().getMonth() == this.gregorianCalendar.month())) {
					
					text += model.getName()+" -> "+model.getDateData().toString()+"\n";
					
				}
				
			}
			
		}
		
		return text;
		
	}
	
	public String getEventDetailsAgenda(Calendar startingDate, Calendar endingDate) {
		
		String text = "";
		
		for(EventModel model: calendarEvents) {
			
			Calendar eventCalendar = Calendar.getInstance();
			eventCalendar.set(Calendar.DAY_OF_MONTH, model.getDateData().getDay());
			eventCalendar.set(Calendar.MONTH, model.getDateData().getMonth());
			eventCalendar.set(Calendar.YEAR, model.getDateData().getYear());
			if(DateData.calendarEquals(startingDate, eventCalendar) || DateData.calendarEquals(endingDate, eventCalendar) || 
					(eventCalendar.before(endingDate) && eventCalendar.after(startingDate))) {
				
				text += model.getName()+" -> "+model.getDateData().toString()+"\n";
				
			}
			
		}
		
		return text;
		
	}
	
	public void uploadFile(String filename) {
		
		try {
			
			ArrayList<RepeatingEventModel> models = FileExecutor.getRepeatingEvents(filename);
			for(RepeatingEventModel model: models) {
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, model.getDateData().getYear());
				calendar.set(Calendar.MONTH, model.getMonthStart());
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				
				ArrayList<DayName> allDays = model.getDayNames();
				
				do {
				
					DayName inSelectionDay = DayName.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1];
					if(allDays.contains(inSelectionDay)) {
						
						// Add the event into the calendar.
						Calendar eventCalendar = Calendar.getInstance();
						eventCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
						eventCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
						eventCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
						addEventModel(new EventModel(model.getName(), new DateData(calendar.get(Calendar.DAY_OF_MONTH),
								calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), model.getDateData().getHourStart(),
								model.getDateData().getHourStop())));
						
					}
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					
				} while((calendar.get(Calendar.MONTH) != model.getMonthStop()));
				
			}
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error is decoding: "+e.toString());
		}
		
	}

}
