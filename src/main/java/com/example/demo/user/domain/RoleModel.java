package com.example.demo.user.domain;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;


@Getter
@Setter

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleModel implements Serializable  {



}
