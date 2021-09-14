package br.com.mrms.meetings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeetingCategoryRepository extends JpaRepository<MeetingCategoryRepository, Integer> {

}
