package com.kvs.memcachedb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionHandlingController {
    @ExceptionHandler({Exception.class})
    public String databaseError() {
        return "error";
    }

}
