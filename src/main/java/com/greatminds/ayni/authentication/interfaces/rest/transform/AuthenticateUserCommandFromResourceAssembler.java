package com.greatminds.ayni.authentication.interfaces.rest.transform;

import com.greatminds.ayni.authentication.domain.model.commands.AuthenticateUserCommand;
import com.greatminds.ayni.authentication.interfaces.rest.resources.AuthenticateUserResource;

public class AuthenticateUserCommandFromResourceAssembler {
    public static AuthenticateUserCommand toCommandFromResource(AuthenticateUserResource resource){
        return new AuthenticateUserCommand(
                resource.username(),
                resource.password()
        );
    }
}
