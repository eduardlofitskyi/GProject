package com.lofitskyi.util;

import com.lofitskyi.repository.PersistentException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PersistentExceptionHandler {

    @ExceptionHandler(PersistentException.class)
    public String handle(){
        return "error";
    }
}
