package com.schibsted.spain.friends.legacy.handler;

import com.schibsted.spain.friends.legacy.exception.SignupLegacyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({ SignupLegacyException.class })
    @ResponseBody
    public ResponseEntity<Object> handleEntityNotFoundException(SignupLegacyException ex) {
        final ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
