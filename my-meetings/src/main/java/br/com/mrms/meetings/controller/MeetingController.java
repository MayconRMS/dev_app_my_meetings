package br.com.mrms.meetings.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.service.MeetingService;

@RestController
public class MeetingController {

	@Autowired
	private MeetingService meetingService;

	@GetMapping("/meeting")
	public List<Meeting> allMeetings(@RequestParam Map<String, String> paramets) {
		if (paramets.isEmpty()) {
			return meetingService.getAllMeetings();
		}

		String description = paramets.get("descricao");
		return meetingService.getMeetingsForDescription("%" + description + "%");
	}

	@GetMapping("/meeting/{id}")
	public Meeting meeting(@PathVariable Integer id) {
		return meetingService.getMeetingForId(id);
	}

	@PostMapping("/meeting")
	public Meeting saveMeeting(@Valid @RequestBody Meeting meeting) {
		return meetingService.saveMeeting(meeting);
	}

	@PutMapping("/meeting/{id}")
	public Meeting replaceEmployee(@RequestBody Meeting newMeeting, @PathVariable Integer id) {
		return meetingService.updateEmployee(id, newMeeting);
	}

	@DeleteMapping("/meeting/{id}")
	public void deleteMeeting(@PathVariable Integer id) {
		meetingService.deleteMeetingforId(id);
	}
}
