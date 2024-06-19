package com.example.demo.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.demo.user.domain.UserModel;
import com.example.demo.user.repository.UserRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
    private final UserRepository repository;

  public UserDetailsServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

    UserModel user = repository.findByUsername(username).block();

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .build();
  }
}
