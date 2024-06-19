package com.example.demo.post.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.post.domain.CommentModel;
import com.example.demo.post.domain.PostId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface CommentRepository extends ReactiveMongoRepository<CommentModel, String> {

    // @Tailable
    Flux<CommentModel> findByPost(PostId id);

    Mono<Long> countByPost(PostId id);

}
