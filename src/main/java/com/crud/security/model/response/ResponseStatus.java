package com.crud.security.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ResponseStatus implements Serializable {
    private String code;
    private String message;
    private String description;

    public ResponseStatus(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
