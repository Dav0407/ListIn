package com.igriss.ListIn.user.entity;


import com.igriss.ListIn.security.roles.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@ToString
public class User implements UserDetails { // Agar UserDetails dan implement qilsak Spring shu classni taniydi

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    private String nickName;

    private boolean enableCalling = true;

    @Column(nullable = false)
    private String phoneNumber;

    private LocalTime fromTime = LocalTime.of(0, 0);

    private LocalTime toTime = LocalTime.of(23, 59);

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profileImagePath;

    private Float rating;

    @Column(nullable = false)
    private Boolean isGrantedForPreciseLocation;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreatedDate
    private LocalDateTime dateCreated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // Har bir Userda unikal field ishlatilishi kerak
    }
}
