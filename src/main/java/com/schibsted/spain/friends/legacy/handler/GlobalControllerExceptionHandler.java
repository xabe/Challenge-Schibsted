package com.schibsted.spain.friends.legacy.handler;

import com.schibsted.spain.friends.legacy.exception.FriendShipException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({ FriendShipException.class })
    @ResponseBody
    public ResponseEntity<Object> handleEntityNotFoundException(FriendShipException ex) {
        final ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
