package com.igriss.ListIn.user.service;


import com.igriss.ListIn.security.security_dto.ChangePasswordRequestDTO;
import com.igriss.ListIn.user.entity.User;

import java.security.Principal;
import java.util.UUID;

public interface UserService {
    void changePassword(ChangePasswordRequestDTO request, Principal connectedUser);

    User findByEmail(String username);

    User findById(UUID id);

    User getUserById(UUID userId);
}
