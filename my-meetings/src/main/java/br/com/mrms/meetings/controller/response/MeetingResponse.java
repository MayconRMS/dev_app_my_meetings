package br.com.mrms.meetings.controller.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingResponse {

	private Integer id;
	private String description;
	private String status;
	private LocalDate dateMeeting;
	private Integer meetingCategoryId;
	private Integer userId;
}
