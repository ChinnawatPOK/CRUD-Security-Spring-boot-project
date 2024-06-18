package com.crud.security.constants;

public enum StatusResponseEnum {
    RESPONSE_STATUS_CS2000("CS2000", "Success", "Success"),
    RESPONSE_STATUS_CS2010("CS2010", "Created", "Information saved"),
    RESPONSE_STATUS_CS4040("CS4040", "Data not found", "Data not found"),
    RESPONSE_STATUS_CS5000("CS5000", "Internal server error", "Internal server error");


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    private String code;
    private String message;
    private String description;

    private StatusResponseEnum(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
