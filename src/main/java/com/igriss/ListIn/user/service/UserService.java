package com.igriss.ListIn.user.service;


import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.security.security_dto.ChangePasswordRequestDTO;
import com.igriss.ListIn.user.dto.FollowsDTO;
import com.igriss.ListIn.user.dto.UpdateResponseDTO;
import com.igriss.ListIn.user.dto.UserRequestDTO;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.UUID;

public interface UserService {

    void changePassword(ChangePasswordRequestDTO request, Principal connectedUser);

    Boolean isFollowingToUser(User followedUser, User followingUser);

    User findByEmail(String username);

    UserResponseDTO findById(UUID id, Authentication authentication);

    UserResponseDTO getUserDetails(Authentication authentication);

    void updateContactDetails(PublicationRequestDTO request, User connectedUser);

    UpdateResponseDTO updateUserDetails(UserRequestDTO userRequestDTO, HttpServletRequest request, Authentication authentication);

    Page<FollowsDTO> getFollowers(UUID userId, int page, int size);

    Page<FollowsDTO> getFollowings(UUID userId, int page, int size);

    UserResponseDTO followToUser(UUID followingUserId, Authentication authentication) throws BadRequestException;

    UserResponseDTO unFollowFromUser(UUID followedUserId, Authentication authentication) throws BadRequestException;
}
