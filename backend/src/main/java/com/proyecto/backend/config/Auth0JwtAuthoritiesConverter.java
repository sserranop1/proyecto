package com.proyecto.backend.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class Auth0JwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        Object permissionsClaim = jwt.getClaims().get("permissions");
        if (permissionsClaim instanceof Collection<?> permissions) {
            authorities.addAll(
                    permissions.stream()
                            .filter(Objects::nonNull)
                            .map(Object::toString)
                            .map(permission -> new SimpleGrantedAuthority("PERM_" + permission))
                            .collect(Collectors.toSet())
            );
        }

        String scopeClaim = jwt.getClaimAsString("scope");
        if (scopeClaim != null && !scopeClaim.isBlank()) {
            authorities.addAll(
                    Arrays.stream(scopeClaim.split(" "))
                            .filter(scope -> !scope.isBlank())
                            .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                            .collect(Collectors.toSet())
            );
        }

        return authorities;
    }
}