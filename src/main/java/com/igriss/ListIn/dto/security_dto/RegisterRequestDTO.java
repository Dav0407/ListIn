package com.igriss.ListIn.dto.security;

import com.igriss.ListIn.security.roles.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String firstname;
    private String lastname;
    private Integer age;
    private String phoneNumber;
    private String email;
    private String password;
    private Role roles;
}
