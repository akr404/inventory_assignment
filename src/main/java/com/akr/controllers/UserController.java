package com.akr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.akr.model.Authority;
import com.akr.model.Item;
import com.akr.model.User;
import com.akr.services.ItemService;
import com.akr.services.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	ItemService itemService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("errorMsg", "Your username and password are invalid.");

		if (logout != null)
			model.addAttribute("msg", "You have been logged out successfully.");

		return "login";
	}
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView loginUser() {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView modelAndView = new ModelAndView("user");
		String username = user.getUsername();
		List<Authority> role = userService.getUserAuthority(username);
		if(role.get(0).getAuthority().equals("seller"))
		{
			modelAndView = new ModelAndView("seller");
			List<Item> listItems = itemService.getAllItemsBySeller(username);
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", "seller");
		}
		else
		{
			modelAndView = new ModelAndView("user");
			List<Item> listItems = itemService.getAllItems();
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", "user");
		}
		modelAndView.addObject("name", username);
		return modelAndView;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(Model model) {
		model.addAttribute("register", new User());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("register"); 
		return modelAndView;
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView addNewUSer(@ModelAttribute("user") User user) {
		//encode passwrd
		String pass = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passEncoded = encoder.encode(pass);
		
		String role = user.getRole();
		if(role == null || role.isEmpty()) role = "user";
		else role = "seller";
		
		try
		{
			user.setPassword(passEncoded);
			userService.insertUser(user);
			userService.insertUserAuthority(user);
		}
		catch(Exception e)
		{
			ModelAndView modelAndView = new ModelAndView("error");
			return modelAndView;
		}
		
		ModelAndView modelAndView = new ModelAndView("reg_success");
		return modelAndView;
	}
	
	@RequestMapping(value = "/reg_success", method = RequestMethod.GET)
	public ModelAndView reg_success(Model model) {
		ModelAndView modelAndView = new ModelAndView("reg_success");
		return modelAndView;
	}

	@RequestMapping(value = "/seller", method = RequestMethod.GET)
	public ModelAndView loginSeller() {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView modelAndView = new ModelAndView("user");
		String username = user.getUsername();
		List<Authority> role = userService.getUserAuthority(username);
		if(role.get(0).getAuthority().equals("seller"))
		{
			modelAndView = new ModelAndView("seller");
			List<Item> listItems = itemService.getAllItemsBySeller(username);
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", "seller");
		}
		else
		{
			modelAndView = new ModelAndView("user");
			List<Item> listItems = itemService.getAllItems();
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", "user");
		}
		modelAndView.addObject("name", username);
		return modelAndView;
	}
	
}
