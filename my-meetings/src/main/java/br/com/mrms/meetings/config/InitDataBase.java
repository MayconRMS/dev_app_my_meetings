package br.com.mrms.meetings.config;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.mrms.meetings.model.ERole;
import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.MeetingCategory;
import br.com.mrms.meetings.model.MeetingStatus;
import br.com.mrms.meetings.model.Role;
import br.com.mrms.meetings.model.User;
import br.com.mrms.meetings.repository.MeetingCategoryRepository;
import br.com.mrms.meetings.repository.MeetingRepository;
import br.com.mrms.meetings.repository.RoleRepository;
import br.com.mrms.meetings.repository.UserRepository;

// h2 = http://localhost:8080/mymeetings/h2-console/

@Configuration
@Profile("dev")
public class InitDataBase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MeetingCategoryRepository meetingCategoryRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Bean
	CommandLineRunner executar() {
		return args -> {
			insertMeeting();
			insertMeeting2();
		};
	}

	private void insertMeeting() {
		Role roleAdmin = new Role(ERole.ROLE_ADMIN);
		roleAdmin = roleRepository.save(roleAdmin);

		User user = new User();
		user.setName("ADMIN");
		user.setPassword(encoder.encode("123456"));
		user.setRoles(Set.of(roleAdmin));
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

	private void insertMeeting2() {
		Role roleUser = new Role(ERole.ROLE_USER);
		roleUser = roleRepository.save(roleUser);

		User user = new User();
		user.setName("MRMS");
		user.setPassword(encoder.encode("123456"));
		user.setRoles(Set.of(roleUser));
		userRepository.save(user);

		MeetingCategory category = new MeetingCategory();
		category.setName("Class");
		meetingCategoryRepository.save(category);

		Meeting meeting = new Meeting();
		meeting.setDescription("Class Spring Security");
		meeting.setDateMeeting(LocalDate.now().plusDays(1));
		meeting.setStatus(MeetingStatus.OPEN);
		meeting.setViseble(true);
		meeting.setCategory(category);
		meeting.setUser(user);
		meetingRepository.save(meeting);
	}

}
