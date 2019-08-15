package com.lendico.challenge.annuityplangenerator.errorhandling;

import lombok.Data;

import java.util.List;

@Data
public class InputValidationException extends RuntimeException {
    private List<String> message;
    public InputValidationException(List<String> message){
        super(message.toString());
        this.message = message;
    }

    public String getMessage(){
        return message==null ? null: message.toString();
    }
}
