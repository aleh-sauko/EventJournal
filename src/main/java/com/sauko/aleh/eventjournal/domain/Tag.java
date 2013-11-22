package com.sauko.aleh.eventjournal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "TAGS")
public class Tag {

	@Id
	@Column (name = "NAME")
	private String name;

	public Tag() {}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
