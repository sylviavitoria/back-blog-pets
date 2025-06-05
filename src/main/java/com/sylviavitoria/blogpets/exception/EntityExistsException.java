package com.sylviavitoria.blogpets.exception;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String message) {
        super(message);
    }
}