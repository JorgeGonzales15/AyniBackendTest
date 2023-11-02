package com.greatminds.ayni.authentication.domain.services;

import com.greatminds.ayni.authentication.domain.model.commands.AuthenticateUserCommand;
import com.greatminds.ayni.authentication.domain.model.commands.CreateUserCommand;

public interface UserCommandService {
    Long handle(CreateUserCommand command);
    Object handle(AuthenticateUserCommand command);
}
