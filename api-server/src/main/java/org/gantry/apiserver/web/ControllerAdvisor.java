package org.gantry.apiserver.web;

import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.gantry.apiserver.exception.NoSuchContainerException;
import org.gantry.apiserver.exception.UserNotFoundException;
import org.gantry.apiserver.web.dto.ErrorResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
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

   @ExceptionHandler(value = UserNotFoundException.class)
   @ResponseStatus(BAD_REQUEST)
   @ResponseBody
   public ErrorResponse handler(UserNotFoundException e, ServletWebRequest req) {
      return ErrorResponse.builder()
              .status(NOT_FOUND)
              .uri(req.getRequest().getRequestURI())
              .message("User not found")
              .detail(e.getMessage())
              .build();
   }

   @ExceptionHandler(value = Exception.class)
   @ResponseStatus(BAD_REQUEST)
   public ErrorResponse handler(Exception e, ServletWebRequest req) {
      e.printStackTrace();
      return ErrorResponse.builder()
              .status(BAD_REQUEST)
              .uri(req.getRequest().getRequestURI())
              .message(BAD_REQUEST.getReasonPhrase())
              .detail(e.getMessage())
              .build();
   }

   @Override
   protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
      ErrorResponse.ErrorResponseBuilder errorResponse = ErrorResponse.builder()
              .status(statusCode);

      if (body instanceof ProblemDetail problem) {
         String uri = problem.getInstance() == null
                 ? extractURI(request)
                 : problem.getInstance().toString();
         errorResponse.uri(uri)
                 .message(problem.getTitle())
                 .detail(problem.getDetail());
      }
      else {
         errorResponse.uri(extractURI(request))
                 .message(extractHttpStatusCodeString(statusCode))
                 .detail(null);
      }


      return new ResponseEntity<>(errorResponse.build(), headers, statusCode);
   }

   private static String extractHttpStatusCodeString(HttpStatusCode statusCode) {
      return HttpStatus.valueOf(statusCode.value()).getReasonPhrase();
   }

   private String extractURI(WebRequest request) {
      if (request instanceof ServletWebRequest servletRequest) {
         return servletRequest.getRequest().getRequestURI();
      }

      return null;
   }

}