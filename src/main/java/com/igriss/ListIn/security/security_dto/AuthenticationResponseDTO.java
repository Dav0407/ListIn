package com.igriss.ListIn.security.security_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    private UserResponseDTO userResponseDTO;
}
