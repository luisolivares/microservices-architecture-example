package com.api.challenge.exceptions;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.utils.ChallengeUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpServletRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        final ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        responseApiDTO.setError(apiError);
        return new ResponseEntity<>(responseApiDTO, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex, final HttpHeaders headers, final HttpServletRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), (List<String>) getErrorsMap(errors));
        final ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        responseApiDTO.setError(apiError);
        return new ResponseEntity<>(responseApiDTO, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final HttpHeaders headers, final HttpServletRequest request) {
        log.info(ex.getClass().getName());
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        final ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        responseApiDTO.setError(apiError);
        return new ResponseEntity<>(responseApiDTO, headers, apiError.getStatus());
    }

    // 404
    @ExceptionHandler({NoHandlerFoundException.class})
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpServletRequest request) {
        log.info(ex.getClass().getName());
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), Arrays.asList(error));
        final ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        responseApiDTO.setError(apiError);
        return new ResponseEntity<>(responseApiDTO, headers, apiError.getStatus());
    }

    // 405
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         WebRequest request) {
        log.info(ex.getClass().getName());
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" Method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), Arrays.asList(builder.toString()));
        final ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        responseApiDTO.setError(apiError);
        return new ResponseEntity<>(responseApiDTO, headers, apiError.getStatus());
    }

    // 415
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpServletRequest request) {
        log.info(ex.getClass().getName());
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" Media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), Arrays.asList(builder.substring(0, builder.length() - 2)));
        final ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        responseApiDTO.setError(apiError);
        return new ResponseEntity<>(responseApiDTO, headers, apiError.getStatus());
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final HttpServletRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), Arrays.asList("error occurred"));
        final ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        responseApiDTO.setError(apiError);
        return new ResponseEntity<>(responseApiDTO, new HttpHeaders(), apiError.getStatus());
    }

}