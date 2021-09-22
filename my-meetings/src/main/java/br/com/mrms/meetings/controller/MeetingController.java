package br.com.mrms.meetings.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mrms.meetings.controller.request.MeetingRequest;
import br.com.mrms.meetings.controller.response.MeetingResponse;
import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.service.MeetingService;

@RestController
public class MeetingController {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/meeting")
	public List<MeetingResponse> allMeetings(@RequestParam Map<String, String> paramets) {
		List<Meeting> listMeeting = new ArrayList<>();

		if (paramets.isEmpty()) {
			listMeeting = meetingService.getAllMeetings();
		} else {
			String description = paramets.get("descricao");
			listMeeting = meetingService.getMeetingsForDescription("%" + description + "%");
		}
		return listMeeting.stream().map(meeting -> modelMapper.map(meeting, MeetingResponse.class))
				.collect(Collectors.toList());
	}

	@GetMapping("/meeting/{id}")
	public MeetingResponse meeting(@PathVariable Integer id) {
		Meeting meeting = meetingService.getMeetingForId(id);
		return modelMapper.map(meeting, MeetingResponse.class);
	}

	@PostMapping("/meeting")
	public MeetingResponse saveMeeting(@Valid @RequestBody MeetingRequest meetingRequest) {
		Meeting meeting = modelMapper.map(meetingRequest, Meeting.class);
		return modelMapper.map(meetingService.saveMeeting(meeting), MeetingResponse.class);
	}

	@PutMapping("/meeting/{id}")
	public MeetingResponse updateMeeting(@RequestBody MeetingRequest meetingRequest, @PathVariable Integer id) {
		Meeting meeting = modelMapper.map(meetingRequest, Meeting.class);
		return modelMapper.map(meetingService.updateMeeting(id, meeting), MeetingResponse.class);
	}

	@DeleteMapping("/meeting/{id}")
	public void deleteMeeting(@PathVariable Integer id) {
		meetingService.deleteMeetingforId(id);
	}
}
