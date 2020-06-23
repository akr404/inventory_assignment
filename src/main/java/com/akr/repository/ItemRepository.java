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
		return template.query("select * from item", new BeanPropertyRowMapper(Item.class));
		
	}
	
	public List<Item> getAllItemsByCategory(String category)
	{
		return template.query("select * from item where category = ?",  new Object[] {category}, new BeanPropertyRowMapper(Item.class));
		
	}

	public List<Item> getAllItemsByid(Integer id)
	{
		return template.query("select * from item where id = ?",  new Object[] {id}, new BeanPropertyRowMapper(Item.class));
		
	}
	
	public List<Item> getAllItemsBySeller(String seller)
	{
		return template.query("select * from item where seller = ?",  new Object[] {seller}, new BeanPropertyRowMapper(Item.class));
		
	}
	
	
	public int deleteById(Integer id)
	{
		 String SQL = "delete from item where id = ?";
		 return template.update(SQL,id);
	}
	
	public int update(Item t)
	{
		 String SQL = "update item set name = ?, category = ?, description = ? where id = ?";
		 return template.update(SQL,t.getName(), t.getCategory(),t.getDescription(), t.getId());
	}
	public int insert(Item t)
	{
		return template.update("insert into item(id, name, category, description, seller)"
				+ " values (?,?,?,?,?)", 
				 new Object[] {t.getId(), t.getName(), t.getCategory(),t.getDescription(), t.getSeller()});
	}
}

