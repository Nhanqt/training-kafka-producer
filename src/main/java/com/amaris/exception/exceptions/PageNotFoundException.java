package com.amaris.exception.exceptions;

public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(String message){
        super(message);
    }
}
