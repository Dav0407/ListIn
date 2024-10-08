package com.igriss.ListIn.entity;


import com.igriss.ListIn.security.roles.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails { // Agar UserDetails dan implement qilsak Spring shu classni taniydi

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    private String firstName;

    private Integer age;



    @Column(nullable = false)
    private String lastName;

    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String profileImagePath;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // Har bir Userda unikal field ishlatilishi kerak
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}
