package com.sauko.aleh.eventjournal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sauko.aleh.eventjournal.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/admin")
	public String listUsers(Model model) {
		model.addAttribute("userList", userService.listUser());
		return "admin";
	}
	
	@RequestMapping("/addAdmin/{userId}")
	public String addAdmin(@PathVariable("userId") Integer userId) {
		userService.setAdmin(userId);
		return "redirect:/admin";
	}
	
	@RequestMapping("/setNewPass/{userId}")
	public String addNewPass(@PathVariable("userId") Integer userId) {
		userService.setNewPass(userId);
		return "redirect:/admin";
	}

	@RequestMapping("/delete/{userId}")
	public String deleteContact(@PathVariable("userId") Integer userId) {
		userService.removeUser(userId);
		return "redirect:/admin";
	}
}
