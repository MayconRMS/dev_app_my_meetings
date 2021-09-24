package br.com.mrms.meetings.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mrms.meetings.controller.assembler.MeetingModelAssembler;
import br.com.mrms.meetings.controller.request.MeetingRequest;
import br.com.mrms.meetings.controller.response.MeetingResponse;
import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.service.MeetingService;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MeetingModelAssembler meetingModelAssembler;

	@GetMapping
	public CollectionModel<EntityModel<MeetingResponse>> allMeetings(@RequestParam Map<String, String> paramets) {
		List<Meeting> listMeeting = new ArrayList<>();

		if (paramets.isEmpty()) {
			listMeeting = meetingService.getAllMeetings();
		} else {
			String description = paramets.get("descricao");
			listMeeting = meetingService.getMeetingsForDescription("%" + description + "%");
		}
		List<EntityModel<MeetingResponse>> meetingModel = listMeeting.stream().map(meetingModelAssembler::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(meetingModel,
				linkTo(methodOn(MeetingController.class).allMeetings(new HashMap<>())).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<MeetingResponse> oneMeeting(@PathVariable Integer id) {
		Meeting meeting = meetingService.getMeetingForId(id);
		return meetingModelAssembler.toModel(meeting);
	}

	@PostMapping
	public MeetingResponse saveMeeting(@Valid @RequestBody MeetingRequest meetingRequest) {
		Meeting meeting = modelMapper.map(meetingRequest, Meeting.class);
		return modelMapper.map(meetingService.saveMeeting(meeting), MeetingResponse.class);
	}

	@PutMapping("/{id}")
	public MeetingResponse updateMeeting(@RequestBody MeetingRequest meetingRequest, @PathVariable Integer id) {
		Meeting meeting = modelMapper.map(meetingRequest, Meeting.class);
		return modelMapper.map(meetingService.updateMeeting(id, meeting), MeetingResponse.class);
	}

	@DeleteMapping("/{id}")
	public void deleteMeeting(@PathVariable Integer id) {
		meetingService.deleteMeetingforId(id);
	}

	@PutMapping("/{id}/start")
	public MeetingResponse startMeeting(@PathVariable Integer id) {
		return modelMapper.map(meetingService.startMeetingForId(id), MeetingResponse.class);
	}

	@PutMapping("/{id}/finish")
	public MeetingResponse finishMeeting(@PathVariable Integer id) {
		return modelMapper.map(meetingService.finishMeetingForId(id), MeetingResponse.class);
	}

	@PutMapping("/{id}/canceled")
	public MeetingResponse cancelMeeting(@PathVariable Integer id) {
		return modelMapper.map(meetingService.cancelMeetingForId(id), MeetingResponse.class);
	}

	@PutMapping("/{id}/open")
	public MeetingResponse openMeeting(@PathVariable Integer id) {
		return modelMapper.map(meetingService.openMeetingForId(id), MeetingResponse.class);
	}

}
