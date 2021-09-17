package br.com.mrms.meetings.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.repository.MeetingRepository;

@Service
public class MeetingService {

	@Autowired
	private MeetingRepository meetingRepository;

	public List<Meeting> getAllMeetings() {
		return meetingRepository.findAll();
	}

	public List<Meeting> getMeetingsForDescription(String description) {
		return meetingRepository.findByDescriptionLike("%" + description + "%");

	}

	public Meeting getMeetingForId(Integer id) {
		return meetingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	public Meeting saveMeeting(Meeting meeting) {
		return meetingRepository.save(meeting);
	}

	public Meeting replaceEmployee(Meeting newMeeting, Integer id) {
		return meetingRepository.findById(id).map(meeting -> {
			meeting.setDescription(newMeeting.getDescription());
			return meetingRepository.save(meeting);
		}).orElseGet(() -> {
			newMeeting.setId(id);
			return meetingRepository.save(newMeeting);
		});
	}

	public void deleteMeetingforId(Integer id) {
		meetingRepository.deleteById(id);
	}

}
