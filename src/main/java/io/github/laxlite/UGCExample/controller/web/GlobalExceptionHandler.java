package io.github.laxlite.UGCExample.controller.web;

import io.github.laxlite.UGCExample.exception.PageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "io.github.laxlite.UGCExample.controller.web")
public class GlobalExceptionHandler {
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePNFE(){
        return "user/fragment/not-found/404";
    }
}
