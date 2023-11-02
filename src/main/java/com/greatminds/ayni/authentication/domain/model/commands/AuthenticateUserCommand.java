package com.greatminds.ayni.authentication.domain.model.commands;

public record AuthenticateUserCommand(
        String username,
        String password
) {
}
