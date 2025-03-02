package com.igriss.ListIn.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO implements Serializable {

    private String profileImagePath;

    private String nickName;

    private String phoneNumber;

    private Boolean isGrantedForPreciseLocation;

    private String locationName;

    private UUID cityId;

    private UUID countryId;

    private UUID stateId;

    private UUID countyId;

    private Double longitude;

    private Double latitude;

    private String biography;

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime fromTime = LocalTime.of(0, 0);

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime toTime = LocalTime.of(23, 59);

    private Boolean isBusinessAccount;

}
