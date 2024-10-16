package com.igriss.ListIn.service;


import com.igriss.ListIn.dto.ChangePasswordRequestDTO;
import com.igriss.ListIn.entity.User;

import java.security.Principal;
import java.util.UUID;

public interface UserService {
    void changePassword(ChangePasswordRequestDTO request, Principal connectedUser);

    User findByEmail(String username);

    User findById(UUID id);
}
