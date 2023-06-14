package com.glo.app.exception;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(String message){
        super(message);
    }
}
