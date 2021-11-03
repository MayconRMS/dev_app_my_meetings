package br.com.mrms.meetings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mrms.meetings.model.ERole;
import br.com.mrms.meetings.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(ERole name);

}


