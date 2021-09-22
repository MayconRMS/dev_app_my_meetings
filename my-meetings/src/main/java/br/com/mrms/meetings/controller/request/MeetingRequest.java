package br.com.mrms.meetings.controller.request;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingRequest {

	private Integer id;

	@NotBlank(message = "{meeting.description.not-blank}")
	@Size(min = 5, max = 150, message = "{meeting.description.size}")
	private String description;

	@FutureOrPresent(message = "{meeting.description.future-or-present}")
	private LocalDate dateMeeting;

	@NotNull(message = "{meeting.category.not-null}")
	@Min(value = 1, message = "{meeting.category.min}")
	private Integer meetingCategoryId;

	@NotNull(message = "{meeting.user.not-null}")
	@Min(value = 1, message = "{meeting.user.min}")
	private Integer userId;

}
