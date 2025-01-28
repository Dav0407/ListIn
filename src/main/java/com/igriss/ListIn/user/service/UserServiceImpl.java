package com.igriss.ListIn.user.service;


import com.igriss.ListIn.exceptions.UserNotFoundException;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.security.roles.Role;
import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import com.igriss.ListIn.security.security_dto.ChangePasswordRequestDTO;
import com.igriss.ListIn.security.service.AuthenticationServiceImpl;
import com.igriss.ListIn.security.service.JwtService;
import com.igriss.ListIn.user.dto.UpdateResponseDTO;
import com.igriss.ListIn.user.dto.UserRequestDTO;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.mapper.UserMapper;
import com.igriss.ListIn.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationServiceImpl authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override // todo -> will be fixed the logical bug
    public void changePassword(ChangePasswordRequestDTO request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        //check if users password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            var exception = new BadCredentialsException("Wrong password");
            log.error("Current password is incorrect", exception);
            throw exception;
        }
        //check if the confirmation password matches with the new password
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            var exception = new BadCredentialsException("Passwords does not match");
            log.error("Passwords does not match", exception);
            throw exception;
        }
        //update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        //save user back to database
        userRepository.save(user);
    }

    @Override
    @Transactional
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UpdateResponseDTO updateUserDetails(UserRequestDTO userRequestDTO, HttpServletRequest request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Integer status = userRepository.updateUserDetails(
                user.getUserId(),
                userRequestDTO.getNickName(),
                userRequestDTO.getProfileImagePath(),
                userRequestDTO.getPhoneNumber(),
                userRequestDTO.getIsGrantedForPreciseLocation(),
                userRequestDTO.getLocationName(),
                userRequestDTO.getLongitude(),
                userRequestDTO.getLatitude(),
                userRequestDTO.getFromTime(),
                userRequestDTO.getToTime()
        );

        userRepository.updateUserRole(user.getUserId(), userRequestDTO.getIsBusinessAccount() ? Role.BUSINESS_SELLER.name() : Role.INDIVIDUAL_SELLER.name());

        User updatedUser = userRepository.getUserByUserId(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (status != 0) log.info("User updated: {}", user);
        else log.info("User update failed: {}", user);

        return UpdateResponseDTO.builder()
                .tokens(
                        authenticationService.generateNewTokens(updatedUser,request)
                )
                .updatedUserDetails(
                        userMapper.toUserResponseDTO(status != 0 ? updatedUser : user)
                ).build();
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

    public UserResponseDTO getUserDetails(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userMapper.toUserResponseDTO(user);
    }
}
