package br.com.mrms.meetings.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.mrms.meetings.controller.UserController;
import br.com.mrms.meetings.controller.response.UserResponse;
import br.com.mrms.meetings.model.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<UserResponse>> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EntityModel<UserResponse> toModel(User user) {

		UserResponse userResponse = modelMapper.map(user, UserResponse.class);

		EntityModel<UserResponse> meetingModel = EntityModel.of(userResponse,
				linkTo(methodOn(UserController.class).oneUser(user.getId())).withSelfRel());

		return meetingModel;
	}

}
