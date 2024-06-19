package com.example.demo.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;
import java.util.List;

@Builder
@Data 
@Component
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private String userId;
    private String lastName;
    // private String password; 프론트로 보내는 값은 비번을 지운다
    private String firstName;
    private String username;
    // private List<RoleModel> roles;
}




