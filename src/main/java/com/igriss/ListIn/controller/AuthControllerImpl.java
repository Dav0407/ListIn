package com.igriss.ListIn.controller;


import com.igriss.ListIn.dto.LoginRequestDTO;
import com.igriss.ListIn.dto.RegisterRequestDTO;
import com.igriss.ListIn.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationService authenticationService;

    private RegisterRequestDTO user;

    public String authenticate_get() {
        return "login";
    }

    public void authenticate_post(LoginRequestDTO loginRequest,
                                  HttpServletResponse response) throws IOException {
        authenticationService.authenticate(loginRequest);
        response.sendRedirect("/");
    }

    public String register_get() {
        System.out.println("got register");
        return "register";

    }

    public void register_post(RegisterRequestDTO user,
                              HttpServletResponse response) throws IOException {
        this.user = user;
        response.sendRedirect("email-ver");
    }

    public String verification_get(Model model) {
        model.addAttribute("email", user.getEmail());
        authenticationService.sendMail(user);
        return "verification";
    }

    public void verification_post(String code,
                                  HttpServletResponse response) throws IOException {
        if (authenticationService.verifyMail(code)) {
            authenticationService.register(user);
            response.sendRedirect("/");
        } else response.sendRedirect("email-ver");
    }

    public void updatePass_put(RegisterRequestDTO requestDTO) {

    }

    public void updateUser_put(RegisterRequestDTO requestDTO) {

    }

}
