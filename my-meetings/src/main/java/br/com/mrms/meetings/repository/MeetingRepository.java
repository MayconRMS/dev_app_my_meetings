package br.com.mrms.meetings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mrms.meetings.model.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

}
