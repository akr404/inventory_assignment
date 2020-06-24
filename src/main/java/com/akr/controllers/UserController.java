package com.akr.controllers;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.akr.utils.*;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("errorMsg", Constants.ERROR_UNABLE_TO_LOGIN);

		if (logout != null)
			model.addAttribute("msg", Constants.LOGOUT_SUCCESS);

		return "login";
	}
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView loginUser() {
		logger.debug("login----");
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_USER);
		String username = user.getUsername();
		List<Authority> role = userService.getUserAuthority(username);
		if(role.get(0).getAuthority().equals(Constants.SELLER))
		{
			modelAndView = new ModelAndView(Constants.VIEW_SELLER);
			List<Item> listItems = itemService.getAllItemsBySeller(username);
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", Constants.SELLER);
		}
		else
		{
			modelAndView = new ModelAndView(Constants.VIEW_USER);
			List<Item> listItems = itemService.getAllItems();
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", Constants.USER);
		}
		modelAndView.addObject("name", username);
		return modelAndView;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(Model model) {
		model.addAttribute("register", new User());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.VIEW_REGISTER); 
		return modelAndView;
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView addNewUSer(@ModelAttribute("user") User user) {
		logger.debug("register---");
		//encode passwrd
		String pass = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passEncoded = encoder.encode(pass);
		
		String role = user.getRole();
		if(role == null || role.isEmpty()) role = Constants.USER;
		else role = Constants.SELLER;
		
		try
		{
			user.setPassword(passEncoded);
			userService.insertUser(user);
			userService.insertUserAuthority(user);
		}
		catch(Exception e)
		{
			logger.error("unable to resiter:{}", e.toString());
			ModelAndView modelAndView = new ModelAndView(Constants.VIEW_ERROR);
			return modelAndView;
		}
		
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_REGISTER_SUCCESS);
		return modelAndView;
	}
	
	@RequestMapping(value = "/reg_success", method = RequestMethod.GET)
	public ModelAndView reg_success(Model model) {
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_REGISTER);
		return modelAndView;
	}

	@RequestMapping(value = "/seller", method = RequestMethod.GET)
	public ModelAndView loginSeller() {
		logger.debug("return seller page-----");
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_USER);
		String username = user.getUsername();
		List<Authority> role = userService.getUserAuthority(username);
		if(role.get(0).getAuthority().equals(Constants.SELLER))
		{
			modelAndView = new ModelAndView(Constants.VIEW_SELLER);
			List<Item> listItems = itemService.getAllItemsBySeller(username);
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", Constants.SELLER);
		}
		else
		{
			modelAndView = new ModelAndView(Constants.VIEW_USER);
			List<Item> listItems = itemService.getAllItems();
			modelAndView.addObject("listItems", listItems);
			modelAndView.addObject("type", Constants.USER);
		}
		modelAndView.addObject("name", username);
		return modelAndView;
	}
	
}
