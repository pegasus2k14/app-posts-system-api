package com.curso.react.exceptions;

public class EmailExistException extends RuntimeException{

   public  EmailExistException(String message){
     //Pasamos el mensaje al constructor de la clase padre
     super(message);
   }
}
