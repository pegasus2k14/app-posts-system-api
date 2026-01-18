package com.curso.react.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.curso.react.model.response.ErrorMesage;

//Esta anotacion de Spring permite manejar errores de 
//manera global en una aplicacion, es decir nos 
//permite  capturar y procesar excepciones desde un 
//solo punto, evitando repetir codigo en cada 
//controlador 
@ControllerAdvice
public class AppExceptionHandler {
    
    @ExceptionHandler(value = EmailExistException.class)
    public ResponseEntity<Object>
    handleEmailExistException(EmailExistException ex, WebRequest request){
        ErrorMesage errorMesage = new ErrorMesage(new Date(), ex.getMessage());
       return new ResponseEntity<>(errorMesage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object>
    handleException(Exception ex, WebRequest request){
        ErrorMesage errorMesage = new ErrorMesage(new Date(), ex.getMessage());
       return new ResponseEntity<>(errorMesage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
