package com.igriss.ListIn.user.controller;


import com.igriss.ListIn.user.dto.UpdateResponseDTO;
import com.igriss.ListIn.user.dto.UserRequestDTO;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @PatchMapping("/update")
    public UpdateResponseDTO updateProfile(@RequestBody UserRequestDTO userRequestDTO, HttpServletRequest request, Authentication authentication) {
        return userService.updateUserDetails(userRequestDTO, request, authentication);
    }


    @GetMapping()
    public UserResponseDTO getUserDetails(Authentication authentication) {
        return userService.getUserDetails(authentication);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUserInfo(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

}
