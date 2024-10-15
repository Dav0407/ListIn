package com.igriss.ListIn.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    private String firstName;

    private Integer age;

    private String lastName;

    private String phoneNumber;

    @Email
    private String email;

    private String password;

    private String profileImagePath;

}
