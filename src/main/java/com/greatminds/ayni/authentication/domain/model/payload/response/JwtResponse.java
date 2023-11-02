package com.greatminds.ayni.authentication.domain.model.payload.response;

import java.util.List;

public record JwtResponse(
        String token,
        String type,
        Long id,
        String username,
        String email,
        List<String> Roles
) {
}
