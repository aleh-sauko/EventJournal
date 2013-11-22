package com.sauko.aleh.eventjournal.dao;

import com.sauko.aleh.eventjournal.domain.Comment;

public interface CommentDAO {
	
	public void addComment(Comment comment);
	public Comment getComment(Integer commentId);
	public void removeComment(Integer commentId);
}
