package com.blog.exception;

import com.blog.payloaddto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice//acts like catch block
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {//this is like gloabal catch block

    @ExceptionHandler(ResorceNotFoundException.class)//telling lets handle all the exceptions related tho this class
    public ResponseEntity<ErrorDetails> resourceNotFoundException(
        ResorceNotFoundException exception,
        WebRequest webRequest //it will give us the details like where the exception occures
    ) {
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//by handling all the exceptins
    @ExceptionHandler(Exception.class)//telling lets handle all the exceptions related tho this class
    public ResponseEntity<ErrorDetails> HandleGlobalException(
            Exception exception,
            WebRequest webRequest //it will give us the details like where the exception occures
    ) {
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
