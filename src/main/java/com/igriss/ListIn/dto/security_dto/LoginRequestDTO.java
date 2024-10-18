package com.igriss.ListIn.dto.security;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {
    private String email;
    private String password;
}
