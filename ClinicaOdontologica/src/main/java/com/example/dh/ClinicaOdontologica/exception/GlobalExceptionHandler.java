package com.example.dh.ClinicaOdontologica.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.apache.log4j.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
    //ExceptionHandler para cuando no se encuentra la entidad buscada en la base de datos.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> noEncontrado(EntityNotFoundException e, WebRequest webRequest){
        logger.error(e.getMessage());
        return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.NOT_FOUND);
    }

    //ExceptionHandler para bad request (petición errónea)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> peticionMalFormada(BadRequestException e, WebRequest webRequest){
        logger.error(e.getMessage());
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
