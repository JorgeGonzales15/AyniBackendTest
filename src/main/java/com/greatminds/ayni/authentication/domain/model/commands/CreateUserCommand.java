package com.greatminds.ayni.authentication.domain.model.commands;

import java.util.Set;

public record CreateUserCommand(
        String username,
        String email,
        String password,
        String role
) {
}
