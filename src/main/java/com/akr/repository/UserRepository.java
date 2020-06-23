package com.akr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.akr.model.Authority;
import com.akr.model.Item;
import com.akr.model.User;

@Repository
public class UserRepository {
	@Autowired
	JdbcTemplate template;
	
	//login
	public List getUserByUserName(String username)
	{
		return template.query("select * from users where username = ?",  new Object[] {username}, new BeanPropertyRowMapper(User.class));
	}
	
	public List getUserAuthority(String username)
	{
		return template.query("select * from authorities where username = ?",  new Object[] {username}, new BeanPropertyRowMapper(Authority.class));
	}	
	//register
	public int insertUser(User t)
	{
		return template.update("insert into users(enabled, username, password)"
				+ " values (?,?,?)", 
				 new Object[] {t.getEnabled() , t.getUsername(),t.getPassword()});
	}
	
	public int insertUserAuthority(User t)
	{
		return template.update("insert into authorities(username, authority)"
				+ " values (?,?)", 
				 new Object[] {t.getUsername(),t.getRole()});
	}
	
	
}
