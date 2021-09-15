package br.com.mrms.meetings.controller;

import java.util.List;
import java.util.Map;

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
import br.com.mrms.meetings.repository.MeetingRepository;

@RestController
public class MeetingController {

	@Autowired
	private MeetingRepository meetingRepository;

	@GetMapping("/meeting")
	public List<Meeting> allMeetings(@RequestParam Map<String, String> paramets) {
		if (paramets.isEmpty()) {
			return meetingRepository.findAll();
		}

		String description = paramets.get("descricao");
		return meetingRepository.findByDescriptionLike("%" + description + "%");

	}

	@GetMapping("/meeting/{id}")
	public Meeting meeting(@PathVariable Integer id) {
		return meetingRepository.findById(id).orElse(null);
	}

	@PostMapping("/meeting")
	public Meeting saveMeeting(@RequestBody Meeting meeting) {
		return meetingRepository.save(meeting);
	}

	@PutMapping("/meeting/{id}")
	public Meeting replaceEmployee(@RequestBody Meeting newMeeting, @PathVariable Integer id) {
		return meetingRepository.findById(id).map(meeting -> {
			meeting.setDescription(newMeeting.getDescription());
			return meetingRepository.save(meeting);
		}).orElseGet(() -> {
			newMeeting.setId(id);
			return meetingRepository.save(newMeeting);
		});
	}

	@DeleteMapping("/meeting/{id}")
	public void deleteMeeting(@PathVariable Integer id) {
		meetingRepository.deleteById(id);
	}
}
