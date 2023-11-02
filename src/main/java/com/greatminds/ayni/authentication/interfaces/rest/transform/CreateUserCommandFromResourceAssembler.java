package com.greatminds.ayni.authentication.interfaces.rest.transform;

import com.greatminds.ayni.authentication.domain.model.commands.CreateUserCommand;
import com.greatminds.ayni.authentication.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {

    public static CreateUserCommand toCommandFromResource(CreateUserResource resource){
        return new CreateUserCommand(
                resource.username(),
                resource.email(),
                resource.password(),
                resource.role()
        );
    }
}
