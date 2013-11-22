package com.sauko.aleh.eventjournal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sauko.aleh.eventjournal.domain.Event;
import com.sauko.aleh.eventjournal.domain.User;
import com.sauko.aleh.eventjournal.service.EventService;
import com.sauko.aleh.eventjournal.service.TagService;
import com.sauko.aleh.eventjournal.service.UserService;
import com.sauko.aleh.eventjournal.util.Classifier;

@Controller
public class CommonController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping("/access_denied")
	public String accessDenied() {
		return "error403";
	}
	
	@RequestMapping("/")
	public String home() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userName != null && !userName.equals("guest")) {
			User user =  userService.getUser(userName);
			if (user == null) System.out.println(userName);
			return "redirect:/index?lang=" + user.getLanguage();
		}
		return "redirect:/index";
	}
	
	@RequestMapping("{url}?lang={lang}")
	public String setLang(@PathVariable("url") String url, @PathVariable("lang") String lang) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userName != null && userName != "guest") {
			User user =  userService.getUser(userName);
			user.setLanguage(lang);
		}
		return "redirect:/" + url;
	}
	
	@Cacheable("index")
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("eventList", eventService.listFixEvent(10));
		model.addAttribute("userList", userService.listTopUser(10));
		model.addAttribute("tags", tagService.listTagsNames());
		return "index";
	}
	
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public @ResponseBody List<String> showTags(Model model) {
		return tagService.listTagsNames();
	}
	
	@RequestMapping("/reindex")
	public String reindex(Model model) {
		eventService.reindex();
		return "redirect:/index";
	}
	
	@RequestMapping("/search")
	public String search(Model model) {
		model.addAttribute("eventsList", null);
		model.addAttribute("createEvent", false);
		model.addAttribute("title", "search");
		return "events";
	}
	
	@RequestMapping("/categories")
	public String categories(Model model) {
		model.addAttribute("categories", Classifier.values());
		return "categories";
	}
	
	@RequestMapping("/categories/{category}")
	public String category(@PathVariable("category") String category, Model model) {
		List<Event> events = eventService.listEventInCategory(category);
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("createEvent", false);
		model.addAttribute("title", category);
		return "events";
	}
	
	@RequestMapping("/search/{content}")
	public String search(@PathVariable("content") String content, Model model) {
		List<Event> events = eventService.search(content);
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("createEvent", false);
		model.addAttribute("title", "search");
		return "events";
	}
	
	@RequestMapping("/search/tag/{content}")
	public String searchTag(@PathVariable("content") String content, Model model) {
		List<Event> events = eventService.searchTag(content);
		model.addAttribute("eventsList", (events != null ? Lists.reverse(events) : null));
		model.addAttribute("createEvent", false);
		model.addAttribute("title", "search");
		return "events";
	}
}
