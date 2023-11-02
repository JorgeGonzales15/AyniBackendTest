package com.greatminds.ayni.authentication.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateUserResource(
        @NotBlank
        @Size(min = 3, max = 20)
        String username,
        @NotBlank
        @Size(max = 50)
        @Email
        String email,
        String role,
        @NotBlank
        @Size(min = 6, max = 40)
        String password
) {
}
