package com.ngs.gateway.config;

import com.ngs.gateway.dto.ResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class HandlerAccessDenied {

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseWrapper<Object> handleAuthorizationException(AccessDeniedException ex) {
        log.error("Authorization Error: ", ex);
        return new ResponseWrapper<>("403", "Không có quyền truy cập");
    }
}
