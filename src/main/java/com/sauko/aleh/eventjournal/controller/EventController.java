package com.sauko.aleh.eventjournal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.sauko.aleh.eventjournal.domain.Event;
import com.sauko.aleh.eventjournal.service.CommentService;
import com.sauko.aleh.eventjournal.service.EventService;
import com.sauko.aleh.eventjournal.service.UserService;
import com.sauko.aleh.eventjournal.util.Classifier;

@Controller
public class EventController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/events_all")
	public String listEvents(Model model) {
		List<Event> events = eventService.listEvent();
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("eventsList", Lists.reverse(eventService.listEvent()));
		model.addAttribute("title", "events_all");
		return "events";
	}
	
	@RequestMapping("/events_my")
	public String listMyEvents(Model model) {
		List<Event> events = eventService.listMyEvent();
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("createEvent", true);
		model.addAttribute("title", "events_my");
		return "events";
	}
	
	@RequestMapping("/events_subscriptions")
	public String listSubscriptionsEvents(Model model) {
		List<Event> events = eventService.listMySubscriptionsEvent();
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("createEvent", true);
		model.addAttribute("title", "subscriptions");
		return "events";
	}
	
	@RequestMapping("/events_income")
	public String listIncomeEvents(Model model) {
		List<Event> events = eventService.listIncomeEvent();
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("createEvent", false);
		model.addAttribute("title", "events_income");
		return "events";
	}
	
	@RequestMapping("/events_past")
	public String listPastEvents(Model model) {
		List<Event> events = eventService.listPastEvent();
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("createEvent", false);
		model.addAttribute("title", "events_past");
		return "events";
	}
	
	@RequestMapping("/event_create")
	public String eventCreate(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("categories", Classifier.values());
		return "event_create";
	}
	
	@RequestMapping("/event/{eventId}")
	public String event(@PathVariable("eventId") Integer eventId, Model model) {
		Event event = eventService.getEvent(eventId);
		model.addAttribute("event", event);
		model.addAttribute("inEvent", userService.inEvent(eventId));
		return "event";
	}
	
	@RequestMapping("/event_edit/{eventId}")
	public String eventEdit(@PathVariable("eventId") Integer eventId, Model model) {
		model.addAttribute("event", eventService.getEvent(eventId));
		model.addAttribute("categories", Classifier.values());
		return "event_create";
	}
	
	@RequestMapping("/event/{eventId}/photos")
	public String eventPhotos(@PathVariable("eventId") Integer eventId, Model model) {
		model.addAttribute("event", eventService.getEvent(eventId));
		return "photos";
	}
	
	@RequestMapping("/event_join/{eventId}")
	public String eventJoin(@PathVariable("eventId") Integer eventId, Model model) {
		userService.addEvent(eventId);
		model.addAttribute("event", eventService.getEvent(eventId));
		model.addAttribute("inEvent", userService.inEvent(eventId));
		return "event";
	}
	
	@RequestMapping("/event_leave/{eventId}")
	public String eventLeave(@PathVariable("eventId") Integer eventId, Model model) {
		userService.removeEvent(eventId);
		model.addAttribute("event", eventService.getEvent(eventId));
		model.addAttribute("inEvent", userService.inEvent(eventId));
		return "event";
	}
	
	@RequestMapping("/event_close/{eventId}")
	public String eventClose(@PathVariable("eventId") Integer eventId, Model model) {
		Event event = eventService.getEvent(eventId);
		eventService.closeEvent(event);
		model.addAttribute("event", event);
		model.addAttribute("inEvent", userService.inEvent(eventId));
		return "event";
	}
	
	
	
	@RequestMapping("/event/{eventId}/{content}")
	public String addComment(@PathVariable("eventId") Integer eventId, 
			@PathVariable("content") String content) {
		commentService.addComment(eventId, content);
		return "redirect:/event/{eventId}";
	}
	
	@RequestMapping(value = "/event_create_press", method = RequestMethod.POST)
	public String eventCreate(
			@ModelAttribute("event") Event event,
			BindingResult result) {	
		if (!event.getEventId().equals(Integer.valueOf(0))) {
			Event data = eventService.getEvent(event.getEventId());
			event.setUsers(data.getUsers());
			event.setComments(data.getComments());
			eventService.updateEvent(event);
			return "redirect:/event/" + event.getEventId(); 
		}
		eventService.addEvent(event);
		return "redirect:/events_my";
	}	
	
}
