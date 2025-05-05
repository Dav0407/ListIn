package com.igriss.ListIn.security.service;


import com.igriss.ListIn.exceptions.UserHasAccountException;
import com.igriss.ListIn.exceptions.UserNotFoundException;
import com.igriss.ListIn.security.security_dto.AuthenticationRequestDTO;
import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.security.security_dto.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponseDTO register(RegisterRequestDTO request, String language) throws UserHasAccountException;

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) throws UserNotFoundException;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
