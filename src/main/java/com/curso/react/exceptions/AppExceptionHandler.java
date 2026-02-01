package com.curso.react.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.curso.react.model.response.ErrorMesage;
import com.curso.react.model.response.ValidationErrors;

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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object>
    handleException(MethodArgumentNotValidException ex, WebRequest request){
       Map<String, String> errors = new HashMap<>();
       //Recorremos todos los errores generados a la hora de validar el modelo y se agregan al Map
       for(ObjectError error: ex.getBindingResult().getAllErrors()){
        //obtenemos el campo que genero el error
        String fieldName = ((FieldError) error).getField();
        //Obtenemos el mensaje del error
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
       }

       ValidationErrors validationErrors = new ValidationErrors(errors, new Date());


       return new ResponseEntity<>(validationErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
