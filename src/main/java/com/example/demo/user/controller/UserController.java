package com.example.demo.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.common.domain.Messenger;
import com.example.demo.user.domain.UserDTO;
import com.example.demo.user.domain.UserModel;
import com.example.demo.user.service.UserService;

@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
public class UserController {


    private final UserService userService;

    @PostMapping("/login")
    public Mono<Messenger> login(@RequestBody UserDTO param) {
        log.info("::: login controller parameter ",param.toString());
        // Messenger messenger = service.login(param);
        Messenger m = Messenger.builder().message("SUCCESS").build();
        Mono<Messenger> helloWorld = Mono.just(m);
        return helloWorld;
    }



    @GetMapping("/logout")
    public Mono<Messenger> logout(@RequestHeader("Authorization") String accessToken) {
        log.info("1- logout request : {}", accessToken);
        Messenger m = Messenger.builder().message("SUCCESS").build();
        Mono<Messenger> logout = Mono.just(m);
        return logout;
    }

    @GetMapping("/tutorials")
  @ResponseStatus(HttpStatus.OK)
  public Flux<UserModel> getAllTutorials(@RequestParam(required = false) String title) {
    if (title == null)
      return userService.findAll();
    else
      return userService.findByTitleContaining(title);
  }

  @GetMapping("/tutorials/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserModel> getTutorialById(@PathVariable("id") String id) {
    return userService.findById(id);
  }

  @PostMapping("/tutorials")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UserModel> createTutorial(@RequestBody UserModel tutorial) {
    return userService.save(new UserModel(tutorial.getTitle(), tutorial.getDescription(), false));
  }

  @PutMapping("/tutorials/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserModel> updateTutorial(@PathVariable("id") String id, @RequestBody UserModel tutorial) {
    return userService.update(id, tutorial);
  }

  @DeleteMapping("/tutorials/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteTutorial(@PathVariable("id") String id) {
    return userService.deleteById(id);
  }

  @DeleteMapping("/tutorials")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAllTutorials() {
    return userService.deleteAll();
  }

  @GetMapping("/tutorials/published")
  @ResponseStatus(HttpStatus.OK)
  public Flux<UserModel> findByPublished() {
    return userService.findByPublished(true);
  }
    
}
