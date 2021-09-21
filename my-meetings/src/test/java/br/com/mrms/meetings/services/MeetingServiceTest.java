package br.com.mrms.meetings.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.mrms.meetings.exception.MeetingStatusException;
import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.MeetingStatus;
import br.com.mrms.meetings.repository.MeetingRepository;
import br.com.mrms.meetings.service.MeetingService;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {

	@Mock
	private MeetingRepository meetingRepository;

	@InjectMocks
	private MeetingService meetingService;
	
	
	@Test
	void NotHaveFinishMeetingCancel() {
		Integer idExemple = 1;
		Meeting meeting = new Meeting();
		meeting.setId(idExemple);
		meeting.setDescription("Teste 01");
		meeting.setStatus(MeetingStatus.CANCELED);
		
		Mockito.when(meetingRepository.findById(idExemple)).thenReturn(Optional.of(meeting));
		
		Assertions.assertThrows(MeetingStatusException.class, () -> meetingService.finishMeetingForId(idExemple));
	}
}
