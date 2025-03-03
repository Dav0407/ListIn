package com.igriss.ListIn.user.mapper;

import com.igriss.ListIn.location.mapper.LocationMapper;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final LocationMapper locationMapper;

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
                .country(locationMapper.toCountryDTO(user.getCountry()))
                .state(locationMapper.toStateDTO(user.getState()))
                .county(locationMapper.toCountyDTO(user.getCounty()))
                .city(locationMapper.toCityDTO(user.getCity()))
                .longitude(user.getLongitude())
                .latitude(user.getLatitude())
                .role(user.getRole())
                .dateCreated(user.getDateCreated())
                .dateUpdated(user.getDateUpdated())
                .build();
    }

    public UserResponseDTO toUserResponseDTO(User user, Boolean following) {
        UserResponseDTO userResponseDTO = toUserResponseDTO(user);
        userResponseDTO.setIsFollowing(following);
        return userResponseDTO;
    }
}
