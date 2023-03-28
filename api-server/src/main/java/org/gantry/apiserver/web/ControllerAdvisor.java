package org.gantry.apiserver.web;

import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {
   @ExceptionHandler(value = NoSuchApplicationException.class)
   public ResponseEntity<Object> exception(NoSuchApplicationException exception) {
      return new ResponseEntity<>("Application not found", HttpStatus.INTERNAL_SERVER_ERROR);
   }
}