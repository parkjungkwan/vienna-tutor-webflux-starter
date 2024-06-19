package com.example.demo.post.repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.post.domain.PostModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface PostRepository extends ReactiveMongoRepository<PostModel, String> {

    Flux<PostModel> findByTitleContains(String q, Pageable pageable);

    Mono<Long> countByTitleContains(String q);

}
