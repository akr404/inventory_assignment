package com.akr.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.akr.model.Item;
@Repository
public class ItemRepository {
	@Autowired
	JdbcTemplate template;
	public List<Item> getAllItems()
	{
		return template.query("select * from item where isdeleted = ?",  new Object[] {false}, new BeanPropertyRowMapper(Item.class));
		
	}
	
	public List<Item> getAllItemsByCategory(String category)
	{
		return template.query("select * from item where category = ? and isdeleted = ?",  new Object[] {category, false}, new BeanPropertyRowMapper(Item.class));
		
	}
	
	public List<Item> getAllItemsForSellerByCategory(String category, String username)
	{
		return template.query("select * from item where seller = ? and category = ? and isdeleted = ?",  
				new Object[] {category, username, false}, new BeanPropertyRowMapper(Item.class));
		
	}

	public List<Item> getAllItemsByid(Integer id)
	{
		return template.query("select * from item where id = ? and isdeleted = ?",  new Object[] {id, false}, new BeanPropertyRowMapper(Item.class));
		
	}
	
	public List<Item> getAllItemsBySeller(String seller)
	{
		return template.query("select * from item where seller = ? and isdeleted = ?",  new Object[] {seller, false}, new BeanPropertyRowMapper(Item.class));
		
	}
	
	
	public int deleteById(Integer id)
	{
		 String SQL = "update item set isDeleted = ? where id = ?";
		 return template.update(SQL,true, id);
	}
	
	public int update(Item t)
	{
		 String SQL = "update item set name = ?, category = ?, description = ? where id = ?";
		 return template.update(SQL,t.getName(), t.getCategory(),t.getDescription(), t.getId());
	}
	public int insert(Item t)
	{
		return template.update("insert into item(id, name, category, description, seller, isdeleted)"
				+ " values (?,?,?,?,?,?)", 
				 new Object[] {t.getId(), t.getName(), t.getCategory(),t.getDescription(), t.getSeller(), t.getIsDeleted()});
	}
}

