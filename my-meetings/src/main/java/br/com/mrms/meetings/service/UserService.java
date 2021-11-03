package br.com.mrms.meetings.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mrms.meetings.model.Role;
import br.com.mrms.meetings.model.User;
import br.com.mrms.meetings.repository.RoleRepository;
import br.com.mrms.meetings.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public User getUserforId(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public List<User> getUsersForName(String name) {
		return userRepository.findByNameLike("%" + name + "%");
	}

	public User saveUser(User user) {
		Set<Role> roles = getRoles(user);
		user.setRoles(roles);
		return userRepository.save(user);
	}

	public User updateUser(Integer id, User User) {
		if (!userRepository.existsById(id))
			throw new EntityNotFoundException();
		User.setId(id);
		return saveUser(User);
	}

	public void deleteUserforId(Integer id) {
		userRepository.deleteById(id);
	}

	private Set<Role> getRoles(User user) {
		Set<Role> rolesBase = user.getRoles().stream().map(role -> roleRepository.findByName(role.getName()))
				.collect(Collectors.toSet());
		return rolesBase;
	}

}
