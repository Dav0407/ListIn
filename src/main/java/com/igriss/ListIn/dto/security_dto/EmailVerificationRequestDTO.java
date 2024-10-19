package com.igriss.ListIn.dto.security_dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailVerificationRequestDTO {
    private  String email;
}
