package com.akr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akr.model.Item;
import com.akr.model.User;
import com.akr.repository.ItemRepository;
import com.akr.repository.UserRepository;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;


	public void insertItem(Item t) {
		itemRepository.insert(t);
	}
	public void updateItem(Item t) {
		itemRepository.update(t);
	}
	
	public void deleteItem(Integer id) {
		itemRepository.deleteById(id);
	}

	
	public List<Item> getAllItems() {
		return itemRepository.getAllItems();
	}
	
	public List<Item> getAllItemsBySeller(String seller) {
		return itemRepository.getAllItemsBySeller(seller);
	}
	
	public List<Item> getAllItemsById(Integer id) {
		return itemRepository.getAllItemsByid(id);
	}
	
	public List<Item> getAllItemsByCategory(String category) {
		return itemRepository.getAllItemsByCategory(category);
	}


}
