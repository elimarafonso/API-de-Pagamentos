package com.elimarAfonso.auth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.elimarAfonso.auth.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		var user = userRepository.findByUser(userName);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("UserName = " + userName + "não encontrado !!!");
		}
	}

}
