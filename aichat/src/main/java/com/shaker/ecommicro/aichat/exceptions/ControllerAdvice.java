package com.shaker.ecommicro.aichat.exceptions;


import com.shaker.ecommicro.aichat.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
      Map<String, String> errors =  new HashMap<>();
      e.getAllErrors()
              .forEach( err ->
                      errors.put(((FieldError) err).getField(), err.getDefaultMessage()));

       return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);

   }

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<APIResponse> handleResourceNotFoundException (ResourceNotFoundException e) {

        return new ResponseEntity<>(APIResponse.builder()
                .status(false)
                .message(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException (Exception e) {
        return new ResponseEntity<>(APIResponse.builder()
                .status(false)
                .message(e.getMessage())
                .build(), HttpStatus.CONFLICT);
    }


}



