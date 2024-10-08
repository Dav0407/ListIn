package com.igriss.ListIn.security;

import com.igriss.ListIn.entity.User;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository repo;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");
            String firstname = oAuth2User.getAttribute("family_name");
            String lastname =oAuth2User.getAttribute("given_name");
            log.info("Authenticated OAuth2 user logged in:\n [{}],\n Email: [{}],\n Name: [{}]",
                    authentication.getAuthorities().toString(), email, firstname+" "+lastname);

            User existingUser = repo.findByEmail(email);
            if (existingUser!=null) {
                log.info("User already exists. Proceeding with login.");
            } else {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setFirstName(firstname);
                newUser.setLastName(lastname);
                repo.save(newUser);
                log.info("New user created and saved: [{}] [{}] [{}]", firstname,lastname, email);
            }
        }
        response.sendRedirect("/user/api/v1/demo");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
