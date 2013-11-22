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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.sauko.aleh.eventjournal.util.CollectionToCSVBridge;



@Entity
@Indexed
@Table(name = "EVENTS")
public class Event {
	
	@Id
	@GeneratedValue
	@Column (name = "EVENT_ID")
	private Integer eventId;

	@Column (name = "TITLE")
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String title;
	
	@Column (name = "DATE")
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String date;
	
	@Column (name = "STATUS")
	private Boolean status;
	
	@Column (name = "PLACE")
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String place;
	
	@Column (name = "CORDINATES")
	private String coordinates;
	
	@Column (name = "DESCRIPTION")
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String description;
	
	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	@Column (name = "AVATAR_ID")
	private String avatarId; 
	
	@Column (name = "TAGS")
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String tags;
	
	@IndexedEmbedded
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<User> users;
	
	@IndexedEmbedded
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "EVENT_COMMENT", joinColumns=@JoinColumn(name = "EVENT_ID"),
				inverseJoinColumns=@JoinColumn(name = "COMMENT_ID"))
	private List<Comment> comments;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name = "EVENT_CLASSIFIER",
		joinColumns=@JoinColumn(name="EVENT_ID")
			)
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name="CLASS_ID") }, generator = "hilo-gen", type = @Type(type="long") )
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl=CollectionToCSVBridge.class)
	private List<String> categories;
	
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name = "EVENT_PHOTOS",
		joinColumns=@JoinColumn(name="EVENT_ID")
			)
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name="PHOTO_ID") }, generator = "hilo-gen", type = @Type(type="long") )
	private List<String> photos;
	
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name = "EVENT_AUDIO",
		joinColumns=@JoinColumn(name="EVENT_ID")
			)
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name="AUDIO_ID") }, generator = "hilo-gen", type = @Type(type="long") )
	private List<String> audio;
	
	
	public List<String> getAudio() {
		return audio;
	}

	public void setAudio(List<String> audio) {
		this.audio = audio;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public Event() {
		this.eventId = 0;
		this.status = true;
		this.avatarId = "eventId";
		this.coordinates = "53.919829, 27.582740";
		this.users = new HashSet<User>();
		this.comments = new ArrayList<Comment>();
		this.categories = new ArrayList<String>();
		this.photos = new ArrayList<String>();
		this.audio = new ArrayList<String>();
	}
	
	public void addTrack(String track) {
		audio.add(track);
	}
	
	public void removeTrack(String trackId) {
		for (int i=0; i<audio.size(); i++)
			if (audio.get(i).equals(trackId))
					audio.remove(i);
	}
	
	public void addPhoto(String photoId) {
		photos.add(photoId);
	}
	
	public void removePhoto(String photoId) {
		for (int i=0; i<photos.size(); i++)
			if (photos.get(i).equals(photoId))
					photos.remove(i);
	}
	
	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public void removeUser(User user) {
		this.users.remove(user);
	}
	
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}

