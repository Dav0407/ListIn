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
                .enableCalling(user.getEnableCalling())
                .phoneNumber(user.getPhoneNumber())
                .fromTime(user.getFromTime())
                .toTime(user.getToTime())
                .email(user.getEmail())
                .followers(user.getFollowers())
                .following(user.getFollowing())
                .biography(user.getBiography())
                .profileImagePath(user.getProfileImagePath())
                .rating(user.getRating())
                .isGrantedForPreciseLocation(user.getIsGrantedForPreciseLocation())
                .locationName(user.getLocationName())
                .longitude(user.getLongitude())
                .latitude(user.getLatitude())
                .role(user.getRole())
                .dateCreated(user.getDateCreated())
                .dateUpdated(user.getDateUpdated())
                .build();
    }
}
