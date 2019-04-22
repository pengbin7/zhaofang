package com.pb.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/403")
    public String accessError(){
        return "403";
    }

    @GetMapping("/404")
    public String notFoundError(){
        return "404";
    }

    @GetMapping("/500")
    public String internalError(){
        return "500";
    }

    @GetMapping("/logout/page")
    public String logoutPage(){
        return "logout";
    }
}
