package com.example.demo.user.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Data
@Document
public class UserModel {

    @Id Long userId ;
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


      @Override
      public String toString() {
        return "UserModel [userId=" + userId +
         ", username=" + username + 
         ", firstName=" + firstName + 
         ", lastName=" + lastName + 
         ", email=" + email + 
         ", password=" + password + 
         "]";
      }


    
}
