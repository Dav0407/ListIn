package com.igriss.ListIn.user.mapper;

import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .profileImagePath(user.getProfileImagePath())
                .businessName(user.getBusinessName())
                .rating(user.getRating())
                .build();
    }
}
