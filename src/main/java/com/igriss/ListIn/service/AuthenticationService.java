package com.igriss.ListIn.service;


import com.igriss.ListIn.dto.AuthenticationRequestDTO;
import com.igriss.ListIn.dto.AuthenticationResponseDTO;
import com.igriss.ListIn.dto.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponseDTO register(RegisterRequestDTO request);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
