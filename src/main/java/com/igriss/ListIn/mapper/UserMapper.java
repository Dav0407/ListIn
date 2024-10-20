package com.igriss.ListIn.mapper;

import com.igriss.ListIn.dto.main_dto.UserDTO;
import com.igriss.ListIn.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
