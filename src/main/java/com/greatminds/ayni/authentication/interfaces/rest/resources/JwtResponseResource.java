package com.greatminds.ayni.authentication.interfaces.rest.resources;

import java.util.List;

public record JwtResponseResource(
        String token,
        String type,
        Long id,
        String username,
        String email,
        List<String> Roles
) {
}
