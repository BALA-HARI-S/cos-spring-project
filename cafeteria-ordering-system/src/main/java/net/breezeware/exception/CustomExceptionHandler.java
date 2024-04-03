package net.breezeware.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.breezeware.entity.ErrorDetail;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FoodItemException.class)
    public ResponseEntity<ErrorDetail> handleFoodItemNotFoundException(FoodItemException exception,
            WebRequest request) {
        log.info("Entering Food Item Exception Handler");
        ErrorDetail errorDetails =
                ErrorDetail.builder().message(HttpStatus.NOT_FOUND.name()).detail(exception.getMessage()).build();
        log.info("Leaving Food Item Exception Handler");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodMenuException.class)
    public ResponseEntity<ErrorDetail> handleFoodMenuNotFoundException(FoodMenuException exception,
            WebRequest request) {
        log.info("Entering Food Menu Exception Handler");
        ErrorDetail errorDetails =
                ErrorDetail.builder().message(HttpStatus.NOT_FOUND.name()).detail(exception.getMessage()).build();
        log.info("Leaving Food Menu Exception Handler");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodOrderException.class)
    public ResponseEntity<ErrorDetail> handleFoodOrderNotFoundException(FoodOrderException exception,
            WebRequest request) {
        log.info("Entering Food Order Exception Handler");
        ErrorDetail errorDetails =
                ErrorDetail.builder().message(HttpStatus.NOT_FOUND.name()).detail(exception.getMessage()).build();
        log.info("Leaving Food Order Exception Handler");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
