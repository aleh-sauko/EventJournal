package com.sauko.aleh.eventjournal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sauko.aleh.eventjournal.domain.User;
import com.sauko.aleh.eventjournal.service.UserService;
import com.sauko.aleh.eventjournal.util.Classifier;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;


	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/registration")
	public String listContacts(Model model) {
		model.addAttribute("user", new User());	
		return "registration";
	}
	
	@RequestMapping(value = "/user_create", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("user") User user,
			BindingResult result) {
		if (userService.getUser(user.getFirstname()) != null) 
			return "redirect:/registration";
		userService.addUser(user);
		return "redirect:/index";
	}
	
	@RequestMapping("/profile")
	public String profile(Model model) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user =  userService.getUser(userName);
		model.addAttribute("user", user);
		model.addAttribute("categories", Classifier.values());		
		return "profile";
	}
	
	@RequestMapping("/profile/{userName}")
	public String profileUserId(@PathVariable("userName") String userName, Model model) {
		User user =  userService.getUser(userName);
		model.addAttribute("user", user);		
		return "profile_view";
	}
	
	@RequestMapping(value = "/profile_change", method = RequestMethod.POST)
	public String changeProfile(@ModelAttribute("user") User user,
			BindingResult result) {
		userService.updateUser(user);
		return "redirect:/index";
	}
	
	@RequestMapping("/password/{userId}")
	public String password(@PathVariable("userId") Integer userId, Model model) {
		User user =  userService.getUser(userId);
		model.addAttribute("user", user);
				
		return "password_change";
	}
	
	@RequestMapping("/password/{userId}/{password}")
	public String changePassword(@PathVariable("userId") Integer userId, 
			@PathVariable("password") String password) {
		User user = userService.getUser(userId);
		user.setPassword(password);
		userService.updateUser(user);
		return "redirect:/profile";
	}
}
