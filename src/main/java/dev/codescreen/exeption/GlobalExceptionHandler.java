package dev.codescreen.exeption;

import dev.codescreen.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> ServerError(Exception e) {
        Error error = new Error(e.getMessage());
        error.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EventStoreException.class)
    public ResponseEntity<Error> handleEventStoreException(EventStoreException e) {
        Error error = new Error(e.getMessage());
        error.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        Error error = new Error(errorMessage);
        error.setCode(String.valueOf(HttpStatus.BAD_REQUEST));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
