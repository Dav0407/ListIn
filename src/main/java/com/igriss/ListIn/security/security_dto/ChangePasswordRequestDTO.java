package com.igriss.ListIn.security.security_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequestDTO {
    private String currentPassword;//todo -> email verification
    private String newPassword;
    private String confirmationPassword;
}
