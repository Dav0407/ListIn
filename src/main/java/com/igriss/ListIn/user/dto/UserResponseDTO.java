package com.igriss.ListIn.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.igriss.ListIn.location.dto.CityResponseDTO;
import com.igriss.ListIn.location.dto.CountryResponseDTO;
import com.igriss.ListIn.location.dto.CountyResponseDTO;
import com.igriss.ListIn.location.dto.StateResponseDTO;
import com.igriss.ListIn.security.roles.Role;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
//todo -> add a jakarta validation to each field
public class UserResponseDTO {

    private UUID id;

    private String nickName;

    private Boolean enableCalling;

    private String phoneNumber;

    private String biography;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime fromTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime toTime;

    private String email;

    private String profileImagePath;

    private Float rating;

    private Boolean isGrantedForPreciseLocation;

    private String locationName;

    private CountryResponseDTO country;

    private StateResponseDTO state;

    private CountyResponseDTO county;

    private CityResponseDTO city;

    private Double longitude;

    private Long followers;

    private Long following;

    private Boolean isFollowing;

    private Double latitude;

    private Role role;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

}
