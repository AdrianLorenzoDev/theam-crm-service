package dev.adrianlorenzo.crmservice.controllers;

import dev.adrianlorenzo.crmservice.resourceExceptions.FileNotAnImageException;
import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.resourceExceptions.StorageException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> resourceNotFoundExceptionHandler(RuntimeException e, WebRequest request) {
        String body = "Resource not found";
        return handleExceptionInternal(e, body,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {FileNotAnImageException.class})
    protected ResponseEntity<Object> fileNotAnImageExceptionHandler(RuntimeException e, WebRequest request) {
        String body = "File is not an image";
        return handleExceptionInternal(e, body,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {InvalidResourceException.class})
    protected ResponseEntity<Object> invalidResourceExceptionHandler(RuntimeException e, WebRequest request) {
        String body = "Invalid resource in request";
        return handleExceptionInternal(e, body,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {StorageException.class})
    protected ResponseEntity<Object> storageExceptionHandler(RuntimeException e, WebRequest request) {
        String body = "An error occurred during image storage";
        return handleExceptionInternal(e, body,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
