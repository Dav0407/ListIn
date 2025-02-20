package com.igriss.ListIn.search.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String homePage(){
        return "index";
    }

    @GetMapping({"/zohoverify/verifyforzoho", "/zohoverify/verifyforzoho.html"})
    public String zohoPage(){
        return "verifyforzoho";
    }
}
