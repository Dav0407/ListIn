package com.igriss.ListIn.search.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        return "privacyPolicy";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicyPage(){
        return "index";
    }

}