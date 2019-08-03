/***
 * Authored By: Hansol Kim, Charles Leon
 */
package com.calendarProject.mvc.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import com.calendarProject.mvc.controllers.CalendarEventController;
import com.calendarProject.mvc.models.DateData;
import com.calendarProject.mvc.models.DayName;
import com.calendarProject.mvc.models.EventModel;

public class CalendarEventView implements ActionListener {

	private CalendarEventController controller;
	/* Calendar Components */
	private int currentDayValue = 0;
	private JButton[][] dayNumber;
	private JLabel monthYearTitle;
	/* Main Components */
	private JFrame frame;
	private JPanel mainPanel;
	/* Movement Components */
	private JPanel dayPanel;
	private JButton todayButton;
	private JButton dayBackButton;
	private JButton dayForwardButton;
	private JPanel monthPanel;
	private JButton monthBackButton;
	private JButton monthForwardButton;
	/* Event View */
	private JButton dayButton;
	private JButton weekButton;
	private JButton monthButton;
	private JTextArea eventsDisplay;
	private JButton addEventButton;
	private JButton fromFileButton;
	/* Agenda */
	private JLabel lblStartingDate;
	private JTextField agendStart;
	private JLabel lblEndingDate;
	private JTextField agendaEnd;
	private JLabel lblMmddyyyy;
	private JButton agendaButton;

	/***
	 * Sets the controller for the view and creates the main UI
	 */
	public CalendarEventView(CalendarEventController controller) {
		this.controller = controller;
		creatingFrame();
	}

	/***
	 * Handles all events in the main UI
	 * @param e The action event that's taken place
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == todayButton) {
			Calendar calendar = controller.getGregorianCalendar().getTodayCalendar();
			this.currentDayValue = controller.getGregorianCalendar().currentDayNumber();
			updateGoogleCalendar(calendar);
		} else if (e.getSource() == monthForwardButton) {
			this.currentDayValue = 1;
			updateGoogleCalendar(controller.getGregorianCalendar().nextMonth());
		} else if (e.getSource() == monthBackButton) {
			this.currentDayValue = 1;
			updateGoogleCalendar(controller.getGregorianCalendar().previousMonth());
		} else if (e.getSource() == dayForwardButton) {
			Calendar calendar = controller.getGregorianCalendar().nextDay();
			this.currentDayValue = controller.getGregorianCalendar().currentDayNumber();
			updateGoogleCalendar(calendar);
		} else if (e.getSource() == dayBackButton) {
			Calendar calendar = controller.getGregorianCalendar().previousDay();
			this.currentDayValue = controller.getGregorianCalendar().currentDayNumber();
			updateGoogleCalendar(calendar);
		} else if (e.getSource() == addEventButton) {
			JTextField titleField = new JTextField();
			JTextField startTimeField = new JTextField();
			JTextField endTimeField = new JTextField();
			Object[] message = {
					"Title:", titleField,
					"Start Time (24 Hour):", startTimeField,
					"End Time (24 Hour):", endTimeField
			};
			int option = JOptionPane.showConfirmDialog(null, message, "Add Event", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					int startH = Integer.parseInt(startTimeField.getText());
					int endH = Integer.parseInt(endTimeField.getText());
					if (!titleField.getText().isEmpty() && startH < endH && startH > 0 && startH <= 23 && endH > 0 && endH <= 24) {
						Calendar calendar = controller.getGregorianCalendar().getCalendar();
						if (controller.addEventModel(new EventModel(titleField.getText(),
								new DateData(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONDAY),
										calendar.get(Calendar.YEAR), startH, endH)))) {
							JOptionPane.showMessageDialog(null, "Event added Successfully.");
						} else {
							JOptionPane.showMessageDialog(null, "Event Failed to add due to clash with other events.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Event Details should be valid.");
					}
					this.eventsDisplay.setText(controller.getEventDetails(CalendarEventController.EVENTS_FOR_DAY));
					this.selectGranularityButton(this.dayButton);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Start and end time should be a number between 0-24");
				}
			}
		} else if (e.getSource() == dayButton) {
			this.controller.setSelectedEventGranularity(CalendarEventController.EVENTS_FOR_DAY);
			this.eventsDisplay.setText(controller.getEventDetails(CalendarEventController.EVENTS_FOR_DAY));
			selectGranularityButton(dayButton);
		} else if (e.getSource() == monthButton) {
			this.controller.setSelectedEventGranularity(CalendarEventController.EVENTS_FOR_MONTH);
			this.eventsDisplay.setText(controller.getEventDetails(CalendarEventController.EVENTS_FOR_MONTH));
			selectGranularityButton(monthButton);
		} else if (e.getSource() == weekButton) {
			this.controller.setSelectedEventGranularity(CalendarEventController.EVENTS_FOR_WEEK);
			this.eventsDisplay.setText(controller.getEventDetails(CalendarEventController.EVENTS_FOR_WEEK));
			selectGranularityButton(weekButton);
		} else if (e.getSource() == agendaButton){
			try {
				Calendar calendarStart = Calendar.getInstance();
				Calendar calendarEnd = Calendar.getInstance();
				String[] startCalendarArray = agendStart.getText().split("/");
				String[] endCalendarArray = agendaEnd.getText().split("/");
				calendarStart.set(Calendar.MONTH, (Integer.parseInt(startCalendarArray[0]) - 1));
				calendarStart.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(startCalendarArray[1])));
				calendarStart.set(Calendar.YEAR, (Integer.parseInt(startCalendarArray[2])));
				calendarEnd.set(Calendar.MONTH, (Integer.parseInt(endCalendarArray[0]) - 1));
				calendarEnd.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(endCalendarArray[1])));
				calendarEnd.set(Calendar.YEAR, (Integer.parseInt(endCalendarArray[2])));
				this.eventsDisplay.setText(controller.getEventDetailsAgenda(calendarStart, calendarEnd));
				this.selectGranularityButton(this.agendaButton);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Format for Date is MM/DD/YYYY");
			}
	} else if (e.getSource() == fromFileButton){
			JFileChooser jFileChooser = new JFileChooser();
			if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				controller.uploadFile(jFileChooser.getSelectedFile().getAbsolutePath());
				JOptionPane.showMessageDialog(null, "Events added Successfully From File..");
			} else {
				JOptionPane.showMessageDialog(null, "Please select file.");
			}
		} else {
			this.currentDayValue = Integer.parseInt(e.getActionCommand());
			this.updateGoogleCalendar(controller.getGregorianCalendar().getCalendarForDay(this.currentDayValue));
		}
	}

	/***
	 * Sets the chosen granularity of the event view to be highlighted and normalizes the unselected options
	 * @param granularityButton The granularity button to be selected
	 */
	private void selectGranularityButton(JButton granularityButton) {
		JButton[] jButtons = {dayButton, monthButton, weekButton, agendaButton};
		for (JButton button : jButtons)
			if (button == granularityButton)
				button.setBackground(new Color(130,212,255));
			else
				button.setBackground(Color.lightGray);
	}

	/***
	 * Regenerates the calendar based on the number of days in the month and updates the event view based on the selected day
	 * @param calendar Any day of the year for which to set the calendar
	 */
	private void updateGoogleCalendar(Calendar calendar) {
		
		int xAxisIndex = 0;
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int yAxisIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int iteratingDayValue = 1;
		
		this.resetCalendarButtons();
		for(int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			if (this.currentDayValue == iteratingDayValue) {
				dayNumber[xAxisIndex][yAxisIndex].setBackground(new Color(128,212,255));
			}
			dayNumber[xAxisIndex][yAxisIndex].setText(iteratingDayValue+"");
			dayNumber[xAxisIndex][yAxisIndex].setVisible(true);
			calendar.set(Calendar.DAY_OF_MONTH, iteratingDayValue);
			
			iteratingDayValue++;
			yAxisIndex++;
			if (yAxisIndex >= 7) {
				yAxisIndex = 0;
				xAxisIndex++;
			}
			
		}
		calendar.set(Calendar.DAY_OF_MONTH, this.currentDayValue);
		monthYearTitle.setText(
				Month.of(calendar.get(Calendar.MONTH) + 1) + " " +calendar.get(Calendar.YEAR)
		);
		if (this.controller.getSelectedEventGranularity() == CalendarEventController.EVENTS_FOR_AGENDA)
			this.controller.setSelectedEventGranularity(CalendarEventController.EVENTS_FOR_DAY);
		this.eventsDisplay.setText(controller.getEventDetails(this.controller.getSelectedEventGranularity()));
	}

	/***
	 * Clears all JButtons representing days in the calendar
	 */
	private void resetCalendarButtons() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				dayNumber[i][j].setBackground(Color.WHITE);
				dayNumber[i][j].setVisible(false);
			}
		}
	}

	/***
	 * Creates the UI of the application
	 */
	private void creatingFrame() {
		frame = new JFrame();
		frame.setTitle("A+ Calendar");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 525);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.lightGray);
		mainPanel.setLayout(null);
		frame.setContentPane(mainPanel);

		JLabel lblGoogleCalendar = new JLabel("A+ CALENDAR");
		lblGoogleCalendar.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblGoogleCalendar.setForeground(Color.WHITE);
		lblGoogleCalendar.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoogleCalendar.setBounds(73, 6, 754, 33);
		mainPanel.add(lblGoogleCalendar);
		
		todayButton = new JButton("Today");
		todayButton.addActionListener(this);
		todayButton.setBackground(Color.WHITE);
		todayButton.setBounds(20, 50, 90, 60);
		mainPanel.add(todayButton);
		
		dayPanel = new JPanel();
		dayPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,
				new Color(255, 255, 255), new Color(255, 255, 255)), "Day", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		dayPanel.setBackground(Color.lightGray);
		dayPanel.setBounds(122, 50, 130, 60);
		mainPanel.add(dayPanel);
		dayPanel.setLayout(null);
		
		dayBackButton = new JButton("<");
		dayBackButton.setBounds(10, 20, 50, 30);
		dayForwardButton = new JButton(">");
		dayForwardButton.setBounds(72, 21, 50, 30);
		for (JButton travButton : new JButton[]{dayBackButton, dayForwardButton}) {
			travButton.addActionListener(this);
			travButton.setBackground(Color.WHITE);
			dayPanel.add(travButton);
		}

		monthPanel = new JPanel();
		monthPanel.setLayout(null);
		monthPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.WHITE,
				new Color(255, 255, 255)),
				"Month", TitledBorder.LEADING,
				TitledBorder.TOP, null,
				new Color(255, 255, 255))
		);
		monthPanel.setBackground(Color.lightGray);
		monthPanel.setBounds(264, 50, 130, 60);
		mainPanel.add(monthPanel);
		
		monthBackButton = new JButton("<");
		monthBackButton.addActionListener(this);
		monthBackButton.setBackground(Color.WHITE);
		monthBackButton.setBounds(10, 20, 50, 30);
		monthPanel.add(monthBackButton);
		
		monthForwardButton = new JButton(">");
		monthForwardButton.addActionListener(this);
		monthForwardButton.setBackground(Color.WHITE);
		monthForwardButton.setBounds(72, 21, 50, 30);
		monthPanel.add(monthForwardButton);
		
		dayButton = new JButton("Day");
		dayButton.addActionListener(this);
		dayButton.setBackground(Color.WHITE);
		dayButton.setBounds(416, 50, 60, 60);
		dayButton.setOpaque(true);
		mainPanel.add(dayButton);

		monthButton = new JButton("Month");
		monthButton.addActionListener(this);
		monthButton.setBackground(Color.WHITE);
		monthButton.setBounds(481, 50, 60, 60);
		monthButton.setOpaque(true);
		mainPanel.add(monthButton);
		
		weekButton = new JButton("Week");
		weekButton.addActionListener(this);
		weekButton.setBackground(Color.WHITE);
		weekButton.setBounds(546, 50, 60, 60);
		weekButton.setOpaque(true);
		mainPanel.add(weekButton);

		fromFileButton = new JButton("From File");
		fromFileButton.addActionListener(this);
		fromFileButton.setBackground(Color.WHITE);
		fromFileButton.setBounds(612, 50, 154, 60);
		mainPanel.add(fromFileButton);
		
		eventsDisplay = new JTextArea();
		eventsDisplay.setEditable(false);
		eventsDisplay.setBounds(416, 122, 344, 258);
		addEventButton = new JButton("Add Event");
		addEventButton.setBackground(new Color(130,212,130));
		addEventButton.setOpaque(true);
		addEventButton.setBounds(416, 380, 344, 50);
		addEventButton.addActionListener(this);
		mainPanel.add(addEventButton);
		mainPanel.add(eventsDisplay);

		/* Agenda UI */
		lblMmddyyyy = new JLabel("MM/DD/YYYY");
		lblMmddyyyy.setForeground(Color.WHITE);
		lblMmddyyyy.setBounds(772, 130, 116, 16);
		mainPanel.add(lblMmddyyyy);

		lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setForeground(Color.WHITE);
		lblStartingDate.setBounds(772, 150, 116, 16);
		mainPanel.add(lblStartingDate);
		
		agendStart = new JTextField();
		agendStart.setColumns(10);
		agendStart.setBounds(772, 178, 100, 26);
		mainPanel.add(agendStart);
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setForeground(Color.WHITE);
		lblEndingDate.setBounds(772, 216, 116, 16);
		mainPanel.add(lblEndingDate);
		
		agendaEnd = new JTextField();
		agendaEnd.setColumns(10);
		agendaEnd.setBounds(772, 244, 100, 26);
		mainPanel.add(agendaEnd);

		agendaButton = new JButton("Agenda");
		agendaButton.addActionListener(this);
		agendaButton.setBackground(Color.WHITE);
		agendaButton.setBounds(772, 280, 100, 40);
		agendaButton.setOpaque(true);
		mainPanel.add(agendaButton);
		/* End Agenda UI */

		/* Month/Year Header */
		monthYearTitle = new JLabel("Month: 1 - Year: 2019");
		monthYearTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		monthYearTitle.setHorizontalAlignment(SwingConstants.CENTER);
		monthYearTitle.setForeground(Color.WHITE);
		monthYearTitle.setBounds(30, 145, 374, 25);
		mainPanel.add(monthYearTitle);
		/* End Month/Year Header */

		/* Weekday Headers */
		DayName[] dayNames = DayName.values();
		JLabel[] week = new JLabel[dayNames.length];
		int xAxis = 20;
		for(int i = 0; i < week.length; i++) {
			week[i] = new JLabel(dayNames[i].toString(),SwingConstants.CENTER);
			week[i].setBounds(xAxis, 195, 50, 25);
			week[i].setFont(new Font("Times", Font.BOLD, 20));
			week[i].setForeground(Color.WHITE);
			xAxis += 55;
			mainPanel.add(week[i]);
		
		}
		/* End Weekday Headers */

		addDayButtons(); //Create days of the month
		dayButton.doClick(); //Set event granularity to default day
		this.frame.setVisible(true);
		
	}
	
	/***
	 * Adds buttons for each day of the month into the calendar panel.
	 */
	private void addDayButtons() {
		int yAxis = 230;
		dayNumber = new JButton[6][7];
		for (int i = 0; i < 6; i++) { //Rows
			int xAxis = 20;
			for (int j = 0; j < 7; j++) { //Columns
				dayNumber[i][j] = new JButton();
				dayNumber[i][j].setBounds(xAxis, yAxis, 50, 35);
				dayNumber[i][j].setBackground(Color.WHITE);
				dayNumber[i][j].setOpaque(true);
				mainPanel.add(dayNumber[i][j]);
				dayNumber[i][j].setEnabled(false);
				xAxis += 55;
			}
			yAxis += 40;
		}
		this.currentDayValue = controller.getGregorianCalendar().currentDayNumber();
		this.updateGoogleCalendar(controller.getGregorianCalendar().getCalendar());
	}
	
}
