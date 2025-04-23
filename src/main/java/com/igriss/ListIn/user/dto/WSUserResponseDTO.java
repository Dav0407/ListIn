package com.igriss.ListIn.user.dto;

import com.igriss.ListIn.user.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WSUserResponseDTO {
    private String nickName;
    private String email;
    private Status status;
}
