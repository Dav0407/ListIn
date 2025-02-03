package com.igriss.ListIn.security.security_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.igriss.ListIn.security.roles.Role;
import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    private String nickName;

    @Builder.Default
    private Boolean enableCalling = true;

    private String phoneNumber;

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime fromTime = LocalTime.of(0, 0);

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime toTime = LocalTime.of(23, 59);

    private String email;
    private String biography;
    private String password;
    private Role roles;
    private Boolean isGrantedForPreciseLocation;
    private String locationName;
    private Double longitude;
    private Double latitude;
}
