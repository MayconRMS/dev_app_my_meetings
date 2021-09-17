package br.com.mrms.meetings.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mrms.meetings.model.Meeting;

@SpringBootTest
public class MeetingRepositoryTest {

	@Autowired
	private MeetingRepository meetingRepository;

	// Teste utilizando query na anotação @query no repository
	@Test
	void testFindByNameCategory() {
		List<Meeting> meeting = meetingRepository.findByNameCategory("Studies");
		Assertions.assertEquals(2, meeting.size());
	}
	
	// Teste utilizando query na anotação @NamedQuery na entidade
	@Test
	void testMeetingForCategory() {
		List<Meeting> meeting = meetingRepository.meetingForCategory("Studies");
		Assertions.assertEquals(2, meeting.size());
	}
}
