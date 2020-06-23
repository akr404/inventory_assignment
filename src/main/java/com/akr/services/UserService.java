package com.akr.services;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.akr.repository.UserRepository;
import com.akr.model.*;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;


	public void insertUser(User user) {
		user.setEnabled(true);
		userRepository.insertUser(user);
	}

	public void insertUserAuthority(User t) {
		userRepository.insertUserAuthority(t);
	}
	public List<User> getUserByUsername(String username) {
		return userRepository.getUserByUserName(username);
	}
	public List<Authority> getUserAuthority(String username) {
		return userRepository.getUserAuthority(username);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
