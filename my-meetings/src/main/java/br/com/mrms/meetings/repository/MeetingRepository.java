package br.com.mrms.meetings.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.MeetingCategory;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

	public List<Meeting> findByDescription(String description);
	
	public List<Meeting> findByDescriptionLike(String description);
	
	public List<Meeting> findByCategory(MeetingCategory meetingCategory);
	
	@Query("select m from Meeting m "
			+ "inner join m.category c "
			+ "where c.name = ?1")
	public List<Meeting> findByNameCategory(String categoryName);
	
	//Utilizando @@NamedQuery na entidade
	public List<Meeting> meetingForCategory(String categoryName);
}
