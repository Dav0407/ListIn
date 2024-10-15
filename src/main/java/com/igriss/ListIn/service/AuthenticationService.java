package com.igriss.ListIn.service;


import com.igriss.ListIn.dto.LoginRequestDTO;
import com.igriss.ListIn.dto.RegisterRequestDTO;
import com.igriss.ListIn.entity.User;
import com.igriss.ListIn.security.verification.EmailVerification;
import com.igriss.ListIn.security.JwtServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final EmailVerification emailVerification;

    private final BCryptPasswordEncoder encoder;

    private final JwtServices jwtService;

    public void authenticate(LoginRequestDTO loginRequest) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        String token = null;
        if (authentication.isAuthenticated()){
            token = jwtService.generateToken(loginRequest.getEmail());
        }
    }
    public void register(RegisterRequestDTO userDTO) {
        User user= new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setPassword(encoder.encode(user.getPassword()));
        String token = jwtService.generateToken(user.getEmail());
        userService.createUser(user);
    }

    public boolean verifyMail( String code) {
        return emailVerification.verifyMail(code);
    }


    public void sendMail(RegisterRequestDTO user) {
        emailVerification.sendMail(user);
    }


}
