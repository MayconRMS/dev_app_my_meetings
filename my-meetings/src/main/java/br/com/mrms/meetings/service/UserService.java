package br.com.mrms.meetings.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mrms.meetings.model.User;
import br.com.mrms.meetings.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserforId(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public List<User> getUsersForName(String name) {
		return userRepository.findByNameLike("%" + name + "%");
	}

	public User saveUser(User User) {
		return userRepository.save(User);
	}

	public User updateUser(Integer id, User User) {
		if (!userRepository.existsById(id))
			throw new EntityNotFoundException();
		User.setId(id);
		return userRepository.save(User);
	}

	public void deleteUserforId(Integer id) {
		userRepository.deleteById(id);
	}

}
