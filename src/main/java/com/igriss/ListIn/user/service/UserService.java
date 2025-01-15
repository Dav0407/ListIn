package com.igriss.ListIn.user.service;


import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.security.security_dto.ChangePasswordRequestDTO;
import com.igriss.ListIn.user.dto.UpdateResponseDTO;
import com.igriss.ListIn.user.dto.UserRequestDTO;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.UUID;

public interface UserService {

    void changePassword(ChangePasswordRequestDTO request, Principal connectedUser);

    User findByEmail(String username);

    User findById(UUID id);

    UserResponseDTO getUserDetails(Authentication authentication);

    void updateContactDetails(PublicationRequestDTO request, User connectedUser);

    UpdateResponseDTO updateUserDetails(UserRequestDTO userRequestDTO, HttpServletRequest request, Authentication authentication);


}
