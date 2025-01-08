package com.igriss.ListIn.user.service;


import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.repository.ProductImageRepository;
import com.igriss.ListIn.publication.service.ProductFileService;
import com.igriss.ListIn.security.roles.Role;
import com.igriss.ListIn.security.security_dto.ChangePasswordRequestDTO;
import com.igriss.ListIn.user.dto.UserRequestDTO;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public void changePassword(ChangePasswordRequestDTO request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        //check if users password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        //check if the confirmation password matches with the new password
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new BadCredentialsException("Passwords does not match");
        }
        //update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        //save user back to database
        userRepository.save(user);
    }

    @Override
    public void updateContactDetails(PublicationRequestDTO request, User connectedUser) {
        userRepository.updateContactDetails(
                connectedUser.getUserId(),
                request.getFromTime(),
                request.getToTime(),
                request.getPhoneNumber(),
                request.getIsGrantedForPreciseLocation()
        );
    }
    @Override
    @Transactional
    public void updateUserDetails(UserRequestDTO userRequestDTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info(user.toString());
        userRepository.updateUserDetails(
                user.getUserId(),
                userRequestDTO.getProfileImagePath(),
                userRequestDTO.getPhoneNumber(),
                userRequestDTO.getIsGrantedForPreciseLocation(),
                userRequestDTO.getLocationName(),
                userRequestDTO.getLongitude(),
                userRequestDTO.getLatitude(),
                userRequestDTO.getFromTime(),
                userRequestDTO.getToTime()
//                userRequestDTO.getIsBusinessAccount() ? Role.BUSINESS_SELLER : Role.INDIVIDUAL_SELLER //todo -> to be done
        );
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.getUserByUserId(userId);
    }
}
