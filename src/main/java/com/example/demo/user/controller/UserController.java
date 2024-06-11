package com.example.demo.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.domain.Messenger;
import com.example.demo.user.domain.UserDTO;

@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO param) {
        log.info("::: login controller parameter ",param.toString());
        // Messenger messenger = service.login(param);
        return ResponseEntity.ok("SUCCESS");
    }



    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) {
        log.info("1- logout request : {}", accessToken);
        // var flag = service.logout(accessToken); // 토큰이 있으면 false, 없으면 true
        return ResponseEntity.ok("SUCCESS");
    }

    
}
