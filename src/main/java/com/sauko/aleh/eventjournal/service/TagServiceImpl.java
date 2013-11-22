package com.sauko.aleh.eventjournal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sauko.aleh.eventjournal.dao.TagDAO;
import com.sauko.aleh.eventjournal.domain.Tag;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagDAO tagDAO;

	@Override
	@Transactional
	public void addTag(Tag tag) {
		tagDAO.addTag(tag);
	}

	@Override
	@Transactional
	public void addTags(String[] arrayTags) {
		for (int i = 0; i < arrayTags.length; i++) {
			Tag tag = new Tag();
			tag.setName(arrayTags[i]);
			tagDAO.addTag(tag);
		}
	}
	
	@Override
	@Transactional
	public List<Tag> listTag() {
		return tagDAO.listTags();
	}
	
	@Override
	@Transactional
	public List<String> listTagsNames() {
		List<Tag> tags = tagDAO.listTags();
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < tags.size(); i++)
			names.add(tags.get(i).getName());
		return names;
	}
}
