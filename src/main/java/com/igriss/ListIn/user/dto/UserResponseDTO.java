package com.igriss.ListIn.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
//todo -> add a jakarta validation to each field
public class UserResponseDTO {

    private UUID id;

    private String nickName;

    private String phoneNumber;

    private String email;

    private String profileImagePath;

    private String businessName;

    private Float rating;

}
