package br.com.mrms.meetings.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mrms.meetings.controller.request.UserRequest;
import br.com.mrms.meetings.controller.response.UserResponse;
import br.com.mrms.meetings.model.User;
import br.com.mrms.meetings.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<UserResponse> allUsers(@RequestParam Map<String, String> paramets) {
		List<User> listUser = new ArrayList<>();

		if (paramets.isEmpty()) {
			listUser = userService.getAllUsers();
		} else {
			String name = paramets.get("name");
			listUser = userService.getUsersForName("%" + name + "%");
		}
		return listUser.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public UserResponse user(@PathVariable Integer id) {
		User user = userService.getUserforId(id);
		return modelMapper.map(user, UserResponse.class);
	}

	@PostMapping
	public UserResponse saveMeeting(@Valid @RequestBody UserRequest userRequest) {
		User user = modelMapper.map(userRequest, User.class);
		return modelMapper.map(userService.saveUser(user), UserResponse.class);
	}

	@PutMapping("/{id}")
	public UserResponse updateMeeting(@RequestBody UserRequest userRequest, @PathVariable Integer id) {
		User user = modelMapper.map(userRequest, User.class);
		return modelMapper.map(userService.updateUser(id, user), UserResponse.class);
	}

	@DeleteMapping("/{id}")
	public void deleteMeeting(@PathVariable Integer id) {
		userService.deleteUserforId(id);

	}

}
