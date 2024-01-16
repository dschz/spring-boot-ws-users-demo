package com.dsc.frm.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author DSchneider
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistsException extends RuntimeException {
    private String msg ;

    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
