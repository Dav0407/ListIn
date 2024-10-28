package com.igriss.ListIn.publication.entity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publications")
public class PublicationController {

    @GetMapping
    public String homePage(){
        return "Home Page";
    }
}
