package com.pb.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author pengbin
 * @date 2019/4/18 11:18
 */
@Controller
public class UserController {

    @GetMapping("/user/login")
    public String loginPage(){
        return "user/login";
    }

    @GetMapping("/user/center")
    public String centerPage(){
        return "user/center";
    }
}
