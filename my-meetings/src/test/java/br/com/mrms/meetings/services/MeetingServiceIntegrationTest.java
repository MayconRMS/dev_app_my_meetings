package br.com.mrms.meetings.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mrms.meetings.exception.MeetingStatusException;
import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.MeetingStatus;
import br.com.mrms.meetings.service.MeetingService;

//Test carrega todo o contexto do spring em memoria
@SpringBootTest
public class MeetingServiceIntegrationTest {

	@Autowired
	private MeetingService meetingService;

	@Test
	void HaveStartMeeting() {
		Meeting meeting = meetingService.startMeetingForId(3);
		Assertions.assertEquals(MeetingStatus.START, meeting.getStatus());
	}

	@Test
	void NotHaveStartMeetingFinish() {
		Meeting meeting = meetingService.getMeetingForId(3);
		meeting.setStatus(MeetingStatus.FINISH);
		meetingService.saveMeeting(meeting);

		Assertions.assertThrows(MeetingStatusException.class, () -> meetingService.finishMeetingForId(3));
	}
}
