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

import br.com.mrms.meetings.controller.assembler.CategoryModelAssembler;
import br.com.mrms.meetings.controller.request.MeetingCategoryRequest;
import br.com.mrms.meetings.controller.response.MeetingCategoryResponse;
import br.com.mrms.meetings.model.MeetingCategory;
import br.com.mrms.meetings.service.MeetingCategoryService;

@RestController
@RequestMapping("/meeting-category")
public class MeetingCategoryController {

	@Autowired
	private MeetingCategoryService meetingCategoryService;

	@Autowired
	private CategoryModelAssembler assembler;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public CollectionModel<EntityModel<MeetingCategoryResponse>> allMeetingsCategory(
			@RequestParam Map<String, String> paramets) {
		List<MeetingCategory> listMeetingCategory = new ArrayList<>();

		if (paramets.isEmpty()) {
			listMeetingCategory = meetingCategoryService.getAllMeetingsCategory();
		} else {
			String name = paramets.get("name");
			listMeetingCategory = meetingCategoryService.getMeetingsCategoryForName("%" + name + "%");
		}

		List<EntityModel<MeetingCategoryResponse>> meetingCategoryModel = listMeetingCategory.stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(meetingCategoryModel,
				linkTo(methodOn(MeetingCategoryController.class).allMeetingsCategory(new HashMap<>())).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<MeetingCategoryResponse> oneMeetingCategory(@PathVariable Integer id) {
		MeetingCategory meetingCategory = meetingCategoryService.getMeetingCategoryForId(id);
		return assembler.toModel(meetingCategory);
	}

	@PostMapping
	public ResponseEntity<EntityModel<MeetingCategoryResponse>> saveMeetingCategory(
			@Valid @RequestBody MeetingCategoryRequest meetingCategoryRequest) {
		MeetingCategory meetingCategory = modelMapper.map(meetingCategoryRequest, MeetingCategory.class);
		MeetingCategory categorySave = meetingCategoryService.saveMeetingCategory(meetingCategory);
		EntityModel<MeetingCategoryResponse> meetingCategoryModel = assembler.toModel(categorySave);
		return ResponseEntity.created(meetingCategoryModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(meetingCategoryModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<MeetingCategoryResponse>> updateMeetingCategory(
			@RequestBody MeetingCategoryRequest meetingCategoryRequest, @PathVariable Integer id) {
		MeetingCategory meetingCategory = modelMapper.map(meetingCategoryRequest, MeetingCategory.class);
		MeetingCategory categorySave = meetingCategoryService.updateMeetingCategory(id, meetingCategory);
		EntityModel<MeetingCategoryResponse> meetingCategoryModel = assembler.toModel(categorySave);
		return ResponseEntity.ok().body(meetingCategoryModel);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMeetingCategory(@PathVariable Integer id) {
		meetingCategoryService.deleteMeetingCategoryforId(id);

	}
}
