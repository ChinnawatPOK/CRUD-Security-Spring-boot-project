package com.crud.security.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseObj implements Serializable {
    private ResponseStatus status;

    public BaseResponseObj(ResponseStatus status) {
        this.status = status;

    }
}
