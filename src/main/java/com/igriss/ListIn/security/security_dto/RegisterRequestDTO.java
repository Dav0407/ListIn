package com.igriss.ListIn.security.security_dto;

import com.igriss.ListIn.security.roles.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String nickName;
    private String phoneNumber;
    private String email;
    private String password;
    private Role roles;
    private String locationName;
    private Double longitude;
    private Double latitude;
}
