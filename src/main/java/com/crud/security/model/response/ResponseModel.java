package com.crud.security.model.response;

import com.crud.security.constants.StatusResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> extends BaseResponseObj {
    @JsonProperty("data")
    private T dataObj;

    public ResponseModel(ResponseStatus status) {
        super(status);
    }

    public ResponseModel(StatusResponseEnum lookup) {
        super(new ResponseStatus(lookup.getCode(), lookup.getMessage(), lookup.getDescription()));
    }

    public ResponseModel<T> setDataObj(T dataObj) {
        this.dataObj = dataObj;
        return this;
    }

    public T getDataObj() {return this.dataObj;}
}
