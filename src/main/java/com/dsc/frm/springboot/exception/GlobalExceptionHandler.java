package com.dsc.frm.springboot.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1) L'utilisation de l'annotation Controller Advice permet de gérer toutes les exceptions transverses<br>
 * 2) Utilisation des "Validation" : En faisant extends la classe de ResponseEntityExceptionHandler on peut surcharger des methods du parent pour adapter le contenu du ResponseEntity
 *
 * @author DSchneider
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ErrorDetails details = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false),
                "USER_NOT_FOUND"
        );
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, WebRequest request){
        ErrorDetails details = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false),
                "USER_EMAIL_ALREADY_EXISTS"
        );
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleOthersException(Exception exception, WebRequest request){
        ErrorDetails details = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }


    /**
     * 2) Afin de ne pas remonter un stack d'err illisbles au ResponseEntity, on peut surcharger les méthodes de l'appel du "Validator"
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //appel provoqué par un pb de validator, l'exception en arg contient les err, qu'on peut reformater pour les renvoyer dans le body.

        Map<String, String> errors = new HashMap<>();
        List<ObjectError> errList = ex.getBindingResult().getAllErrors();

        errList.forEach(err -> {
            String fErr = ((FieldError) err).getField();
            String msg = err.getDefaultMessage();
            errors.put("Champ:"+ fErr, msg);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
