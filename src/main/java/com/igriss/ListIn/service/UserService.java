package com.igriss.ListIn.service;


import com.igriss.ListIn.dto.security_dto.ChangePasswordRequestDTO;
import com.igriss.ListIn.entity.Users;

import java.security.Principal;
import java.util.UUID;

public interface UserService {
    void changePassword(ChangePasswordRequestDTO request, Principal connectedUser);

    Users findByEmail(String username);

    Users findById(UUID id);

    Users getUserById(UUID userId);
}
