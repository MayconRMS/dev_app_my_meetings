package br.com.mrms.meetings.controller.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingRequest {
	
	private Integer id;
	private String description;
	private LocalDate dateMeeting;
	private Integer meetingCategoryId;
	private Integer userId;
}
