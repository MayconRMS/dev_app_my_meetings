package br.com.mrms.meetings.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingCategoryRequest {

	private Integer id;
	
	@NotBlank(message = "{meetingCategory.name.not-blank}")
	@Size(min = 2, max = 50, message = "{meetingCategory.name.size}")
	private String name;
}
