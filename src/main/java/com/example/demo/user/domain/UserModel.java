package com.example.demo.user.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;

import java.io.Serializable;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("users")
public class UserModel implements Serializable{

    @Id String id ;
    String username; // username is email
    String firstName ;
    String lastName ;
   // @Email String email;
    @JsonIgnore String password ;


    @Builder.Default()
    private boolean active = true;

    @Builder.Default()
    private List<String> roles = new ArrayList<>();



      @Override
      public String toString() {
        return "UserModel [id=" + id +
         ", firstName=" + firstName + 
         ", lastName=" + lastName + 
         ", username=" + username + 
         ", password=" + password + 
         "]";
      }


    
}
