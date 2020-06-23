package com.akr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akr.model.Item;
import com.akr.services.ItemService;

@RestController
@RequestMapping(path = "/api")
public class RestControllerService {

	@Autowired
	ItemService  itemService;
     
    @GetMapping(path="/getAllItems", produces = "application/json")
    public List<Item> getAllItems() 
    {
    	UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		
        return itemService.getAllItemsBySeller(username);
    }
    
    @GetMapping(path="/getAllItemsByCategory", produces = "application/json")
    public List<Item> getAllByCategory(@ModelAttribute("category") String category) 
    {
    	UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		
        return itemService.getAllItemsForSellerByCategory(category, username);
    }
    
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public List<Item> addItem(@ModelAttribute("item") Item item) {
		Integer id = null;
		List<Item> items = itemService.getAllItems();
		id = items.get(items.size()-1).getId()+1;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		
		try
		{
			item.setId(id);
			item.setSeller(username);
			itemService.insertItem(item);
		}
		catch(Exception e)
		{
			
		}
		
		return itemService.getAllItemsById(item.getId());
		
	}
	
	@RequestMapping("/deleteItem")
	public String deleteItem(@RequestParam Integer id) {
		
		try
		{
			itemService.deleteItem(id);
		}
		catch(Exception e)
		{
			return "false";
		}
		
		return "true";	
	}
	
	@RequestMapping(value = "/editItem", method = RequestMethod.POST)
	public List<Item> saveItem(@ModelAttribute("item") Item item) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		
		try
		{
			item.setSeller(username);
			itemService.updateItem(item);
		}
		catch(Exception e)
		{
			
		}
		return itemService.getAllItemsById(item.getId());
		
	}
	
}
