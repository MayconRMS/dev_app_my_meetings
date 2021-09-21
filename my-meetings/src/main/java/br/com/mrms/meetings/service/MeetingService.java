package br.com.mrms.meetings.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mrms.meetings.exception.MeetingStatusException;
import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.MeetingStatus;
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

	public Meeting startMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (!MeetingStatus.OPEN.equals(meeting.getStatus()))
			throw new MeetingStatusException();
		
		meeting.setStatus(MeetingStatus.TO_RUNNING);

		meetingRepository.save(meeting);
		return meeting;
	}
	
	public Meeting finishMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (!MeetingStatus.CANCEL.equals(meeting.getStatus()))
			throw new MeetingStatusException();
		
		meeting.setStatus(MeetingStatus.FINISH);

		meetingRepository.save(meeting);
		return meeting;
	}
	
	public Meeting cancelMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (!MeetingStatus.FINISH.equals(meeting.getStatus()))
			throw new MeetingStatusException();
		
		meeting.setStatus(MeetingStatus.CANCEL);

		meetingRepository.save(meeting);
		return meeting;
	}
	
	public Meeting openMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (!MeetingStatus.TO_RUNNING.equals(meeting.getStatus()))
			throw new MeetingStatusException();
		
		meeting.setStatus(MeetingStatus.OPEN);

		meetingRepository.save(meeting);
		return meeting;
	}

}
