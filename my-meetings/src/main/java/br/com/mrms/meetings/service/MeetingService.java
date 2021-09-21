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

	public Meeting updateEmployee(Integer id, Meeting meeting) {
		if (!meetingRepository.existsById(id))
			throw new EntityNotFoundException();
		meeting.setId(id);
		return meetingRepository.save(meeting);
	}

	public void deleteMeetingforId(Integer id) {
		meetingRepository.deleteById(id);
	}

	public Meeting startMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (!MeetingStatus.OPEN.equals(meeting.getStatus()))
			throw new MeetingStatusException();

		meeting.setStatus(MeetingStatus.START);
		return saveMeeting(meeting);
	}

	public Meeting finishMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (MeetingStatus.CANCELED.equals(meeting.getStatus()))
			throw new MeetingStatusException();

		meeting.setStatus(MeetingStatus.FINISH);
		return saveMeeting(meeting);
	}

	public Meeting cancelMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (MeetingStatus.FINISH.equals(meeting.getStatus()))
			throw new MeetingStatusException();

		meeting.setStatus(MeetingStatus.CANCELED);
		return saveMeeting(meeting);
	}

	public Meeting openMeetingForId(Integer id) {
		Meeting meeting = getMeetingForId(id);

		if (MeetingStatus.OPEN.equals(meeting.getStatus()))
			throw new MeetingStatusException();

		meeting.setStatus(MeetingStatus.OPEN);
		return saveMeeting(meeting);
	}

}
