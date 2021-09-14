package br.com.mrms.meetings.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "meetings")
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "description", nullable = false, length = 150)
	private String description;

	@Enumerated(EnumType.STRING)
	private MeetingStatus status;

	private LocalDate dateMeeting;

	private boolean viseble;

	//private MeetingCategory category;

	//private User user;

}
