package org.example.tltravel.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tltravel.exceptions.BaseTlTravelException;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.view.out.ErrorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@RestControllerAdvice
public class ExceptionController {

    @Autowired
    private MessageSource messages;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorView handleException(Exception e,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        ErrorView ev = new ErrorView();
        ev.setExMessage(e.getMessage());
        ev.setTime(LocalDateTime.now());
        ev.setLogId(UUID.randomUUID().toString());
        ev.setPath(request.getContextPath()+"?" + request.getQueryString());
        ev.setMethod(request.getMethod());
        return ev;
    }

    @ExceptionHandler(BaseTlTravelException.class)

    public ErrorView handleException(BaseTlTravelException e,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Locale local) {
        ErrorView ev = new ErrorView();
        ev.setMessage(messages.getMessage(e.getCode(),new Object[0],local));
        ev.setTime(LocalDateTime.now());
        ev.setLogId(UUID.randomUUID().toString());
        ev.setPath(request.getServletPath()+"?" + request.getQueryString());
        ev.setMethod(request.getMethod());
        response.setStatus(e.getStatus());
        return ev;
    }

    private String getQuery(HttpServletRequest request) {
        if(request.getQueryString() != null){
            return "?" + request.getQueryString();
        }
        return "";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ErrorView handleException(MethodArgumentNotValidException e,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Locale local) {
        ErrorView ev = new ErrorView();
        ev.setMessage(e.getTitleMessageCode());
        ev.setTime(LocalDateTime.now());
        ev.setLogId(UUID.randomUUID().toString());
        ev.setPath(request.getServletPath() + request.getQueryString());
        ev.setMethod(request.getMethod());
        return ev;
    }


    @ExceptionHandler(TLEntityNotActive.class)

    public ErrorView handleException(TLEntityNotActive e,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Locale local) {
        ErrorView ev = new ErrorView();
        ev.setMessage(messages.getMessage(e.getCode(),new Object[0],local));
        ev.setExMessage(e.getMessage());
        ev.setTime(LocalDateTime.now());
        ev.setLogId(UUID.randomUUID().toString());
        ev.setPath(request.getServletPath() + request.getQueryString());
        ev.setMethod(request.getMethod());
        response.setStatus(e.getStatus());
        return ev;
    }



}
