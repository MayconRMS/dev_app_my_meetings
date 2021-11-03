package br.com.mrms.meetings.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mrms.meetings.model.User;
import br.com.mrms.meetings.repository.UserRepository;
import br.com.mrms.meetings.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return UserDetailsImpl.build(user);
	}

}
