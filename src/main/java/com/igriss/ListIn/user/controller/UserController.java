package com.igriss.ListIn.user.controller;


import com.igriss.ListIn.user.dto.UserRequestDTO;
import com.igriss.ListIn.user.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/update")
    public void updateProfile(@RequestBody UserRequestDTO userRequestDTO, Authentication authentication) {
        userService.updateUserDetails(userRequestDTO, authentication);
    }
}
