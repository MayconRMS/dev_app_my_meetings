package br.com.mrms.meetings.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mrms.meetings.controller.assembler.UserModelAssembler;
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
	private UserModelAssembler assembler;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public CollectionModel<EntityModel<UserResponse>> allUsers(@RequestParam Map<String, String> paramets) {
		List<User> listUser = new ArrayList<>();

		if (paramets.isEmpty()) {
			listUser = userService.getAllUsers();
		} else {
			String name = paramets.get("name");
			listUser = userService.getUsersForName("%" + name + "%");
		}

		List<EntityModel<UserResponse>> usersModel = listUser.stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(usersModel,
				linkTo(methodOn(UserController.class).allUsers(new HashMap<>())).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<UserResponse> oneUser(@PathVariable Integer id) {
		User user = userService.getUserforId(id);
		return assembler.toModel(user);
	}

	@GetMapping("/all-informations")
	public List<User> allUsersInformations() {
		return userService.getAllUsers();
	}

	@PostMapping
	public ResponseEntity<EntityModel<UserResponse>> saveUser(@Valid @RequestBody UserRequest userRequest) {
		User user = modelMapper.map(userRequest, User.class);
		User userSave = userService.saveUser(user);
		EntityModel<UserResponse> userModel = assembler.toModel(userSave);
		return ResponseEntity.created(userModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(userModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<UserResponse>> updateUser(@RequestBody UserRequest userRequest,
			@PathVariable Integer id) {
		User user = modelMapper.map(userRequest, User.class);
		User userSalve = userService.updateUser(id, user);
		EntityModel<UserResponse> userModel = assembler.toModel(userSalve);
		return ResponseEntity.ok().body(userModel);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUserforId(id);
	}

}
