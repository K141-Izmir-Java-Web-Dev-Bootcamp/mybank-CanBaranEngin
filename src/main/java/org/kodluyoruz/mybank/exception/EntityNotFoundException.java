package org.kodluyoruz.mybank.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName) {
        super("Related " + entityName + " not found!");
    }
}