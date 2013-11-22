package com.sauko.aleh.eventjournal.service;

import java.util.List;

import com.sauko.aleh.eventjournal.domain.Tag;

public interface TagService {
	
	public void addTag(Tag tag);
	
	public void addTags(String[] arrayTag);

	public List<Tag> listTag();
	
	public List<String> listTagsNames();
}
