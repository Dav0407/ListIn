package com.igriss.ListIn.security;

import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.RedirectStrategy;
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
            String nickName = oAuth2User.getAttribute("family_name");
            log.info("Authenticated OAuth2 user logged in:\n [{}],\n Email: [{}],\n Name: [{}]",
                    authentication.getAuthorities().toString(), email, nickName);

            Optional<User> existingUser = repository.findByEmail(email);
            if (existingUser.isPresent())
                log.info("User already exists. Proceeding with login.");
             else {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setNickName(nickName);
                repository.save(newUser);
                log.info("New user created and saved: [{}] [{}]", nickName, email);
            }
        }
        response.sendRedirect("/");
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return super.getRedirectStrategy();
    }
}
