package com.igriss.ListIn.controller;

import com.igriss.ListIn.dto.LoginRequestDTO;
import com.igriss.ListIn.dto.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/auth")
public interface AuthController {

        @GetMapping("/login")
        String authenticate_get();

        @PostMapping("/login")
        void authenticate_post(LoginRequestDTO loginRequest, HttpServletResponse response) throws IOException;

        @GetMapping("/register")
        String register_get();

        @PostMapping("/register")
        void register_post(RegisterRequestDTO user, HttpServletResponse response) throws IOException ;

        @GetMapping("/register/email-ver")
        String verification_get(Model model);

        @PostMapping("/register/email-ver")
        void verification_post(@RequestParam("code") String code, HttpServletResponse response) throws IOException ;

        @PutMapping("/user/update/pass")
        void updatePass_put(@RequestBody RegisterRequestDTO requestDTO);

        @PutMapping("/user/update")
        void updateUser_put(@RequestBody RegisterRequestDTO requestDTO);

}


