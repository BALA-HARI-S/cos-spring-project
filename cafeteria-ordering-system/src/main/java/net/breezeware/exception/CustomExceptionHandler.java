package net.breezeware.exception;

import net.breezeware.entity.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FoodItemException.class)
    public ResponseEntity<ErrorDetail> handleFoodItemNotFoundException(FoodItemException exception, WebRequest request) {
        ErrorDetail errorDetails = ErrorDetail.builder()
                .message(HttpStatus.NOT_FOUND.name())
                .detail(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
