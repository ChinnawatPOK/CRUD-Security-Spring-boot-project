package com.crud.security.exception;

import com.crud.security.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.crud.security.constants.StatusResponseEnum.RESPONSE_STATUS_CS4040;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ResponseModel<Object>> handlerDataNotFoundException(DataNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseModel<>(RESPONSE_STATUS_CS4040));
    }
}
