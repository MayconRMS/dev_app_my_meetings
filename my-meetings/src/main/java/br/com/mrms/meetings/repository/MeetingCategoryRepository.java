package br.com.mrms.meetings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mrms.meetings.model.MeetingCategory;


@Repository
public interface MeetingCategoryRepository extends JpaRepository<MeetingCategory, Integer> {

	List<MeetingCategory> findByNameLike(String string);
}
