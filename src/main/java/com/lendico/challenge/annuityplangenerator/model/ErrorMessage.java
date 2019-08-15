package com.lendico.challenge.annuityplangenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private Date timestamp;
    private int code;
    private String error;
    private List<String> message;

    public List<String> getMessage(){
        if (message==null) return Collections.emptyList();
        else return message;
    }
}
