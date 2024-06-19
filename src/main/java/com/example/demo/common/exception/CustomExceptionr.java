package com.example.demo.common.exception;

public class CustomExceptionr extends RuntimeException{
    private static final long serialVersionUID = -5970845585469454688L;

    public CustomExceptionr(String type) {
        System.out.println("MY-ERROR: "+type + "의 예외가 발생했습니다.");
    }
    
}
