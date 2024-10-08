package com.igriss.ListIn.controller;

import com.igriss.ListIn.entity.Category;
import com.igriss.ListIn.services.RetrieveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class Controller {
    private final RetrieveService retrieve;

    @PostMapping("/login")
    public void login(){

    }

    @PostMapping("/retrieve")
    public void retrieve(@RequestBody Category category){
        retrieve.save(category);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam UUID id){
        retrieve.delete(id);
    }

    @GetMapping("/api/v1/demo")
    public String loggedIn(HttpServletResponse response){
       log.info("Logged in: [{}]",response.getStatus());
       return "Logged in:";
    }

}
