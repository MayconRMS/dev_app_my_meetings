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
	private ModelMapper modelMapper;

	@GetMapping
	public List<MeetingCategoryResponse> allMeetingsCategory(@RequestParam Map<String, String> paramets) {
		List<MeetingCategory> listMeetingCategory = new ArrayList<>();

		if (paramets.isEmpty()) {
			listMeetingCategory = meetingCategoryService.getAllMeetingsCategory();
		} else {
			String name = paramets.get("name");
			listMeetingCategory = meetingCategoryService.getMeetingsCategoryForName("%" + name + "%");
		}
		return listMeetingCategory.stream()
				.map(meetingCategory -> modelMapper.map(meetingCategory, MeetingCategoryResponse.class))
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public MeetingCategoryResponse OneMeetingCategory(@PathVariable Integer id) {
		MeetingCategory meetingCategory = meetingCategoryService.getMeetingCategoryForId(id);
		return modelMapper.map(meetingCategory, MeetingCategoryResponse.class);
	}

	@PostMapping
	public MeetingCategoryResponse saveMeetingCategory(@Valid @RequestBody MeetingCategoryRequest meetingCategoryRequest) {
		MeetingCategory meetingCategory = modelMapper.map(meetingCategoryRequest, MeetingCategory.class);
		return modelMapper.map(meetingCategoryService.saveMeetingCategory(meetingCategory),
				MeetingCategoryResponse.class);
	}

	@PutMapping("/{id}")
	public MeetingCategoryResponse updateMeetingCategory(@RequestBody MeetingCategoryRequest meetingCategoryRequest,
			@PathVariable Integer id) {
		MeetingCategory meetingCategory = modelMapper.map(meetingCategoryRequest, MeetingCategory.class);
		return modelMapper.map(meetingCategoryService.updateMeetingCategory(id, meetingCategory),
				MeetingCategoryResponse.class);
	}

	@DeleteMapping("/{id}")
	public void deleteMeetingCategory(@PathVariable Integer id) {
		meetingCategoryService.deleteMeetingCategoryforId(id);

	}
}
