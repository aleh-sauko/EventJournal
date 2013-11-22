package com.sauko.aleh.eventjournal.dao;

import java.util.List;

import com.sauko.aleh.eventjournal.domain.Tag;

public interface TagDAO {

	public void addTag(Tag tag);
	
	public Tag getTag(String name);
	
	public List<Tag> listTags();
}
