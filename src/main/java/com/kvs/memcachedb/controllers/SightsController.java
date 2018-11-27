package com.kvs.memcachedb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SightsController {

    @GetMapping("/sight")
    String getSight(){
        return "sight";
    }
}
