package com.example.demo.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository  {

    Boolean existsByEmail(String email);


    
}
