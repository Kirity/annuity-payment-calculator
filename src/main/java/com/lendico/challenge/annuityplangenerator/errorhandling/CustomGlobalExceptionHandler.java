package com.lendico.challenge.annuityplangenerator.errorhandling;

import com.lendico.challenge.annuityplangenerator.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public CustomGlobalExceptionHandler() {
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
    }

    private ErrorMessage errorMessage;
    private final HttpHeaders headers;

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<Object> dateValidationHandler(InputValidationException dv){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        errorMessage = new ErrorMessage(new Date(), status.value(), status.name(), new ArrayList<>());
        errorMessage.getMessage().add(dv.getMessage());
        return new ResponseEntity<>(errorMessage, headers, status);
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<Object> unexpectedExceptionHandler(UnexpectedException uex){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(new Date());
        errorMessage.setCode(status.value());
        errorMessage.setError(status.name());
        return new ResponseEntity<>(errorMessage, headers, status);
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() +":"+ x.getDefaultMessage())
                .collect(Collectors.toList());

        errorMessage = new ErrorMessage(new Date(), status.value(), status.name(), errors);
        return new ResponseEntity<>(errorMessage, headers, status);
    }

}
