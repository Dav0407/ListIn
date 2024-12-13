package com.igriss.ListIn.user.mapper;

import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getUserId())
                .nickName(user.getNickName())
                .phoneNumber(user.getPhoneNumber())
                .profileImagePath(user.getProfileImagePath())
                .rating(user.getRating())
                .build();
    }
}
