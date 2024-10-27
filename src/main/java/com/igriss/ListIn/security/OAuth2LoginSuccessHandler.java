package com.igriss.ListIn.security;

import com.igriss.ListIn.entity.Users;
import com.igriss.ListIn.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository repository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");
            String firstname = oAuth2User.getAttribute("family_name");
            String lastname =oAuth2User.getAttribute("given_name");
            log.info("Authenticated OAuth2 user logged in:\n [{}],\n Email: [{}],\n Name: [{}]",
                    authentication.getAuthorities().toString(), email, firstname+" "+lastname);

            Optional<Users> existingUser = repository.findByEmail(email);
            if (existingUser.isPresent())
                log.info("User already exists. Proceeding with login.");
             else {
                Users newUser = new Users();
                newUser.setEmail(email);
                newUser.setFirstName(firstname);
                newUser.setLastName(lastname);
                repository.save(newUser);
                log.info("New user created and saved: [{}] [{}] [{}]", firstname,lastname, email);
            }
        }
        response.sendRedirect("/");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
