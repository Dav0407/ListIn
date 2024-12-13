package com.igriss.ListIn.security.roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {
    INDIVIDUAL_SELLER(Collections.emptySet()), //todo -> will be added necessary authorities
    BUSINESS_SELLER(Collections.emptySet()), //todo -> will be added necessary authorities
    ADMIN(Collections.emptySet()),; //todo -> will be added necessary authorities

    private final Set<String> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return authorities;
    }
}

