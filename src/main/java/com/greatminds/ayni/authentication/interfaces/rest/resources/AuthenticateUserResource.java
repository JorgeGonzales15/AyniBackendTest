package com.greatminds.ayni.authentication.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserResource(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
