package br.com.mrms.meetings.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mrms.meetings.model.MeetingCategory;
import br.com.mrms.meetings.repository.MeetingCategoryRepository;

@Service
public class MeetingCategoryService {

	@Autowired
	private MeetingCategoryRepository meetingCategoryRepository;

	public List<MeetingCategory> getAllMeetingsCategory() {
		return meetingCategoryRepository.findAll();
	}

	public List<MeetingCategory> getMeetingsCategoryForName(String name) {
		return meetingCategoryRepository.findByNameLike("%" + name + "%");
	}

	public MeetingCategory getMeetingCategoryForId(Integer id) {
		return meetingCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	public MeetingCategory saveMeetingCategory(MeetingCategory meetingCategory) {
		return meetingCategoryRepository.save(meetingCategory);
	}

	public MeetingCategory updateMeetingCategory(Integer id, MeetingCategory meetingCategory) {
		if (!meetingCategoryRepository.existsById(id))
			throw new EntityNotFoundException();
		meetingCategory.setId(id);
		return meetingCategoryRepository.save(meetingCategory);
	}

	public void deleteMeetingCategoryforId(Integer id) {
		meetingCategoryRepository.deleteById(id);
	}
	
}
