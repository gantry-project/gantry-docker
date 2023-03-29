package org.gantry.apiserver.web;

import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.gantry.apiserver.exception.NoSuchContainerException;
import org.gantry.apiserver.web.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvisor {
   @ExceptionHandler(value = NoSuchApplicationException.class)
   @ResponseStatus(NOT_FOUND)
   public ErrorResponse handler(NoSuchApplicationException e, ServletWebRequest req) {
      return ErrorResponse.builder()
              .status(NOT_FOUND)
              .uri(req.getRequest().getRequestURI())
              .message("Application not found")
              .detail(String.format("Application id %s does not exist.", e.getId()))
              .build();
   }

   @ExceptionHandler(value = NoSuchContainerException.class)
   @ResponseStatus(NOT_FOUND)
   public ErrorResponse handler(NoSuchContainerException e, ServletWebRequest req) {
      return ErrorResponse.builder()
              .status(NOT_FOUND)
              .uri(req.getRequest().getRequestURI())
              .message("Application not found")
              .detail(String.format("Application id %s does not exist.", e.getId()))
              .build();
   }
}