package br.com.mrms.meetings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mrms.meetings.model.Meeting;
import br.com.mrms.meetings.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByNameLike(String string);
}
