package org.kodluyoruz.mybank.exception;

public class ArithmeticException extends RuntimeException{
    public ArithmeticException(String entityName) {
        super("Insufficient balance !!");
    }
}