package com.igriss.ListIn.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

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

    private Double longitude;

    private Double latitude;

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime fromTime = LocalTime.of(0, 0);

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime toTime = LocalTime.of(23, 59);

    private Boolean isBusinessAccount;

}
