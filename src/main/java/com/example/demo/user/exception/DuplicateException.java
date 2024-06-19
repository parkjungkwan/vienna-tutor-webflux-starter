package com.example.demo.user.exception;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
      }
}
