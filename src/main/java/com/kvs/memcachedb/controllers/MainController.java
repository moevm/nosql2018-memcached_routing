package com.kvs.memcachedb.controllers;

import com.kvs.memcachedb.memcached.MemcachedExample;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    String getMainPage(){
        return "main_page";
    }

    @GetMapping("/about")
    String getAboutPage(){
        return "about";
    }

    @GetMapping("/connect")
    String getConnectPage(){
        return "connect";
    }
}
