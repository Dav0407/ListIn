package com.igriss.ListIn.user.controller;


import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.user.dto.UserRequestDTO;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @PatchMapping("/update")
    public AuthenticationResponseDTO updateProfile(@RequestBody UserRequestDTO userRequestDTO, HttpServletRequest request, Authentication authentication) {
        return userService.updateUserDetails(userRequestDTO, request, authentication);
    }

    @GetMapping()
    public UserResponseDTO getUserDetails(Authentication authentication) {
        return userService.getUserDetails(authentication);
    }
}
