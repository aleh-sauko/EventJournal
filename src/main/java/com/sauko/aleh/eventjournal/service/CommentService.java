package com.sauko.aleh.eventjournal.service;

import com.sauko.aleh.eventjournal.domain.Comment;

public interface CommentService {

	public void addComment(Integer eventId, String content);
	public Comment getComment(Integer id);
	public void removeComment(Integer id);
}
