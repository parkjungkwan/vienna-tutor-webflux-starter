package com.example.demo.common.record;
import jakarta.validation.constraints.NotBlank;
public record AuthenticationRequest (

    @NotBlank String username,
    @NotBlank String password
    
){}
