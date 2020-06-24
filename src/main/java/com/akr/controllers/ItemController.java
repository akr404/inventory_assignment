package com.akr.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.akr.model.Item;
import com.akr.services.ItemService;
import com.akr.utils.Constants;

@RestController
public class ItemController {
	@Autowired
	ItemService  itemService;
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	
	@RequestMapping(value = "/new_item", method = RequestMethod.GET)
	public ModelAndView newItem(@ModelAttribute("item") Item item) {
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_NEW_ITEM);
		return modelAndView;		
	}
	
	
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(@ModelAttribute("item") Item item) {
		logger.debug("Add Item request----");
		Integer id = null;
		List<Item> items = itemService.getAllItems();
		id = items.get(items.size()-1).getId()+1;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_OPS_SUCCESS);
		try
		{
			item.setId(id);
			item.setSeller(username);
			itemService.insertItem(item);
		}
		catch(Exception e)
		{
			logger.error("Unable to add item:{}", e.toString());
			modelAndView = new ModelAndView(Constants.VIEW_OPS_FAILED);
			return modelAndView;
		}
		logger.debug("Item added :{}", item.getName());
		return modelAndView;
		
	}
	
	@RequestMapping("/deleteItem")
	public ModelAndView deleteItem(@RequestParam Integer id) {
		logger.debug("delete item request------");
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_OPS_SUCCESS);
		try
		{
			itemService.deleteItem(id);
		}
		catch(Exception e)
		{
			logger.error("Unable to delete item:{}", e.toString());
			modelAndView = new ModelAndView(Constants.VIEW_OPS_FAILED);
			return modelAndView;
		}
		logger.debug("Item deleted successfully:{}", id);
		return modelAndView;	
	}

	@RequestMapping(value = "/editItem", method = RequestMethod.GET)
	public ModelAndView editItem(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView(Constants.VIEW_EDIT_ITEM);
		Item item = itemService.getAllItemsById(id).get(0);
		mav.addObject("item", item);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/saveItem", method = RequestMethod.POST)
	public ModelAndView saveItem(@ModelAttribute("item") Item item) {
		logger.debug("Update item reqest----");
		ModelAndView modelAndView = new ModelAndView(Constants.VIEW_OPS_SUCCESS);
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		
		try
		{
			item.setSeller(username);
			itemService.updateItem(item);
		}
		catch(Exception e)
		{
			logger.error("unable to update item:{}", e.toString());
			modelAndView = new ModelAndView(Constants.VIEW_OPS_FAILED);
			return modelAndView;
		}
		logger.debug("Item updated successssullt:{}", item.getName());
		return modelAndView;
		
	}
	
	
}
