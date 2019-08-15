package com.lendico.challenge.annuityplangenerator.errorhandling;

import lombok.Data;

@Data
public class UnexpectedException extends RuntimeException {
    private String message;
    public UnexpectedException(String message){
        super(message);
        this.message = message;
    }
}
