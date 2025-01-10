package com.igriss.ListIn.user.dto;

import com.igriss.ListIn.security.security_dto.AuthenticationResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResponseDTO {
    private UserResponseDTO updatedUserDetails;
    private AuthenticationResponseDTO tokens;
}
