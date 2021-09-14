package br.com.mrms.meetings.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meeting {
	
	private String description;
	
	private MeetingStates state;
	
	private LocalDate dateMeeting;
	
	private boolean viseble;
	
	private MeetingCategory category;
	
	private User user;

}
