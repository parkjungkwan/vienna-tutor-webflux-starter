package com.example.demo.user.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

 @Data

public class UserModel {

    Long userId ;
    String username;
    String firstName ;
    String lastName ;
    String email;
    String password ;


    List <RoleModel> roles ;


    public UserModel (String email , String password , List<RoleModel> roles) {
      this.email= email ;
      this.password=password ;
      this.roles=roles ;}


    
}
