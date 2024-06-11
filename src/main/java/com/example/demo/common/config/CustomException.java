package com.example.demo.common.config;

public class CustomException extends RuntimeException{
    private static final long serialVersionUID = -5970845585469454688L;

    public CustomException(String type) {
        System.out.println("MY-ERROR: "+type + "의 예외가 발생했습니다.");
    }
    
}
