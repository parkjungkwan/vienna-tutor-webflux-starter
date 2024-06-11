package com.example.demo.security.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.domain.ProfileDTO;

import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/profile")
    Mono<ProfileDTO> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Mono.just(new ProfileDTO(user.getUsername(), user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(name -> name.substring("ROLE_".length()))
                .collect(Collectors.toSet())
        ));
    }
    
}
