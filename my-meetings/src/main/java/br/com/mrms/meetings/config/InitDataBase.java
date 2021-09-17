package br.com.mrms.meetings.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.MeetingCategory;
import br.com.mrms.meetings.model.MeetingStatus;
import br.com.mrms.meetings.model.User;
import br.com.mrms.meetings.repository.MeetingCategoryRepository;
import br.com.mrms.meetings.repository.MeetingRepository;
import br.com.mrms.meetings.repository.UserRepository;

@Configuration
@Profile("dev")
public class InitDataBase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MeetingCategoryRepository meetingCategoryRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Bean
	CommandLineRunner executar() {
		return args -> {
			insertMeeting();
		};
	}

	private void insertMeeting() {
		User user = new User();
		user.setName("Admin");
		user.setPassword("123456");
		userRepository.save(user);

		MeetingCategory category = new MeetingCategory();
		category.setName("Studies");
		meetingCategoryRepository.save(category);

		Meeting meeting = new Meeting();
		meeting.setDescription("Learn Spring Boot");
		meeting.setDateMeeting(LocalDate.now().plusDays(1));
		meeting.setStatus(MeetingStatus.OPEN);
		meeting.setViseble(true);
		meeting.setCategory(category);
		meeting.setUser(user);
		meetingRepository.save(meeting);
		
		Meeting meeting2 = new Meeting();
		meeting2.setDescription("Learn Spring JPA");
		meeting2.setDateMeeting(LocalDate.now().plusDays(3));
		meeting2.setStatus(MeetingStatus.OPEN);
		meeting2.setViseble(true);
		meeting2.setCategory(category);
		meeting2.setUser(user);
		meetingRepository.save(meeting2);
		
	}

}
