package br.com.mrms.meetings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mrms.meetings.model.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

	public List<Meeting> findByDescription(String description);
	
	public List<Meeting> findByDescriptionLike(String description);
	
}
