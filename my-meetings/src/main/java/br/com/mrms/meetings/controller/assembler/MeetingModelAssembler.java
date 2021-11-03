package br.com.mrms.meetings.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.mrms.meetings.controller.MeetingCategoryController;
import br.com.mrms.meetings.controller.MeetingController;
import br.com.mrms.meetings.controller.UserController;
import br.com.mrms.meetings.controller.response.MeetingResponse;
import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.MeetingStatus;

@Component
public class MeetingModelAssembler implements RepresentationModelAssembler<Meeting, EntityModel<MeetingResponse>> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EntityModel<MeetingResponse> toModel(Meeting meeting) {

		MeetingResponse meetingResponse = modelMapper.map(meeting, MeetingResponse.class);

		EntityModel<MeetingResponse> meetingModel = EntityModel.of(meetingResponse,
				linkTo(methodOn(MeetingController.class).oneMeeting(meetingResponse.getId())).withSelfRel(),
				linkTo(methodOn(MeetingController.class).allMeetings(new HashMap<>())).withRel("meetings"),
				linkTo(methodOn(MeetingCategoryController.class)
						.oneMeetingCategory(meetingResponse.getMeetingCategoryId())).withRel("meetingCategory"),
				linkTo(methodOn(UserController.class).oneUser(meetingResponse.getUserId())).withRel("user"));

		if (MeetingStatus.START.equals(meeting.getStatus())) {
			meetingModel.add(linkTo(methodOn(MeetingController.class).finishMeeting(meeting.getId())).withRel("finish"),
					linkTo(methodOn(MeetingController.class).cancelMeeting(meeting.getId())).withRel("canceled"));
		}

		if (MeetingStatus.OPEN.equals(meeting.getStatus())) {
			meetingModel.add(linkTo(methodOn(MeetingController.class).startMeeting(meeting.getId())).withRel("start"));
		}
		
		if (MeetingStatus.CANCELED.equals(meeting.getStatus())) {
			meetingModel.add(linkTo(methodOn(MeetingController.class).openMeeting(meeting.getId())).withRel("open"));
		}

		if (MeetingStatus.FINISH.equals(meeting.getStatus())) {
			meetingModel.add(linkTo(methodOn(MeetingController.class).openMeeting(meeting.getId())).withRel("open"));
		}

		
		return meetingModel;
	}

}
