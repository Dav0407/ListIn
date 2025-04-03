package com.igriss.ListIn.security.service;

import com.igriss.ListIn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract user details from OAuth provider
        String email = oAuth2User.getAttribute("email");
        String nickName = oAuth2User.getAttribute("name");

        if(email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        log.info("OAuth2 authentication for email: {}", email);

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "email");
    }
}