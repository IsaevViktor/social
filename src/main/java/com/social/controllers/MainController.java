package com.social.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/protected**")
    public String protectedPage() {
        return "protected";
    }

    //Spring Security see this :
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
