package com.greatminds.ayni.authentication.interfaces.rest.transform;

import com.greatminds.ayni.authentication.domain.model.aggregates.User;
import com.greatminds.ayni.authentication.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity){
        return new UserResource(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRoles()
        );
    }
}
