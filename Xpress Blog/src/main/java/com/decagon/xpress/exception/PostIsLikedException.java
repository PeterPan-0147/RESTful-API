package com.decagon.xpress.exception;

import lombok.Getter;

@Getter
public class PostIsLikedException extends RuntimeException{
    private String message;

    public PostIsLikedException(String message) {
        this.message = message;
    }
}
