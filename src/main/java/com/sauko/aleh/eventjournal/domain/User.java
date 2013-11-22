package com.sauko.aleh.eventjournal.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue
	private Integer userId;

	@Column(name = "FIRSTNAME", length = 20)
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String firstname;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "EMAIL", length = 20)
	private String email;
	
	@Column(name = "ROLE")
	private String role;

	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@Column(name="RATING")
	private Integer rate;
	
	@Column(name="AVATAR_ID")
	private String avatarId;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="LANGUAGE", length = 2)
	private String language;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name = "USER_CLASSIFIER",
		joinColumns=@JoinColumn(name="USER_ID")
			)
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name="CLASS_ID") }, generator = "hilo-gen", type = @Type(type="long") )
	private List<String> categories;
	
	public String getLanguage() {
		return language;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return description;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(mappedBy="users")
	private Set<Event> events;
	
	public void addEvent (Event event){
		this.events.add(event);
	}

	public void removeEvent (Event event){
		this.events.remove(event);
	}
	
	public boolean inEvent(Integer eventId){
		boolean result = false;
		for (Event e : events) {
			if (e.getEventId().equals(eventId)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public User() {
		this.events = new HashSet<Event>();
		this.categories = new ArrayList<String>();
		this.setRole("ROLE_USER");
    	this.setEnabled(true);
		this.setLanguage("en");
		this.setRate(0);
		this.setAvatarId("userId");
		this.description = "";
	}
	
	public Set<Event> getEvents() {
		return events;
	}


	public void setEvents(Set<Event> events) {
		this.events = events;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		if(firstname.length()>29)
			this.firstname = firstname.substring(0,29);
		else
			this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		if(lastname.length()>29)
			this.lastname = lastname.substring(0,29);
		else
			this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email.length()>29)
			this.email = email.substring(0,29);
		else
			this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(password);
	}
}
