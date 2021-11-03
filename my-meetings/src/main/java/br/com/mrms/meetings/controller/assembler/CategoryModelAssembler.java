package br.com.mrms.meetings.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.mrms.meetings.controller.MeetingCategoryController;
import br.com.mrms.meetings.controller.response.MeetingCategoryResponse;
import br.com.mrms.meetings.model.MeetingCategory;

@Component
public class CategoryModelAssembler
		implements RepresentationModelAssembler<MeetingCategory, EntityModel<MeetingCategoryResponse>> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EntityModel<MeetingCategoryResponse> toModel(MeetingCategory meetingCategory) {

		MeetingCategoryResponse meetingCategoryResponse = modelMapper.map(meetingCategory,
				MeetingCategoryResponse.class);

		EntityModel<MeetingCategoryResponse> meetingModel = EntityModel.of(meetingCategoryResponse,
				linkTo(methodOn(MeetingCategoryController.class).oneMeetingCategory(meetingCategory.getId()))
						.withSelfRel());

		return meetingModel;
	}

}
