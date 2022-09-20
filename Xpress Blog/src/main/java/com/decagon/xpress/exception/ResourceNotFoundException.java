package com.decagon.xpress.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private String message;
    public ResourceNotFoundException(String message) {this.message = message;}
}
