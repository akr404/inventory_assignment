package com.akr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.akr.model.Item;
import com.akr.model.User;
import com.akr.services.ItemService;

@RestController
public class ItemController {
	@Autowired
	ItemService  itemService;
	
	@RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
	public ModelAndView getAllItems(Model model) {
		return null;
		
	}
	
	@RequestMapping(value = "/new_item", method = RequestMethod.GET)
	public ModelAndView newItem(@ModelAttribute("item") Item item) {
		ModelAndView modelAndView = new ModelAndView("new_item");
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(@ModelAttribute("item") Item item) {
		Integer id = null;
		List<Item> items = itemService.getAllItems();
		id = items.get(items.size()-1).getId()+1;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		

		ModelAndView modelAndView = new ModelAndView("operation_success_item");
		try
		{
			item.setId(id);
			item.setSeller(username);
			itemService.insertItem(item);
		}
		catch(Exception e)
		{
			modelAndView = new ModelAndView("operation_failed_item");
			return modelAndView;
		}
		
		return modelAndView;
		
	}
	
	@RequestMapping("/deleteItem")
	public ModelAndView deleteCustomerForm(@RequestParam Integer id) {
		
		ModelAndView modelAndView = new ModelAndView("operation_success_item");
		try
		{
			itemService.deleteItem(id);
		}
		catch(Exception e)
		{
			modelAndView = new ModelAndView("operation_failed_item");
			return modelAndView;
		}
		
		return modelAndView;	
	}

	@RequestMapping(value = "/editItem", method = RequestMethod.GET)
	public ModelAndView editItem(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView("edit_item");
		Item item = itemService.getAllItemsById(id).get(0);
		mav.addObject("item", item);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/saveItem", method = RequestMethod.POST)
	public ModelAndView saveItem(@ModelAttribute("item") Item item) {
		ModelAndView modelAndView = new ModelAndView("operation_success_item");
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		
		try
		{
			item.setSeller(username);
			itemService.updateItem(item);
		}
		catch(Exception e)
		{
			modelAndView = new ModelAndView("operation_failed_item");
			return modelAndView;
		}
		return modelAndView;
		
	}
	
	
}
