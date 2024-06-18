package com.crud.security.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DataNotFoundException extends RuntimeException
{
    private String code;
    private String message;
    private String description;
    private HttpStatus httpStatus;

    public DataNotFoundException(String code, HttpStatus httpStatus) {
        super(code);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
