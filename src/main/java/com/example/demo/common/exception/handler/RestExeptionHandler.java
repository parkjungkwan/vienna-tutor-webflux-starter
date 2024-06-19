package com.example.demo.common.exception.handler;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.post.exception.PostNotFoundException;

@RestControllerAdvice
@Slf4j
public class RestExeptionHandler  extends ResponseEntityExceptionHandler{
        @ExceptionHandler(PostNotFoundException.class)
    public ProblemDetail handlePostNotFoundException(PostNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Post Not Found");
        problemDetail.setType(URI.create("http://example.com/api/errors/not_found"));
        return problemDetail;
    }
}
