package com.greatminds.ayni.authentication.interfaces.rest.transform;

import com.greatminds.ayni.authentication.domain.model.payload.response.JwtResponse;
import com.greatminds.ayni.authentication.interfaces.rest.resources.JwtResponseResource;

public class AuthenticationResourceFromAuthenticationResponseAssembler {
    public static JwtResponseResource toResourceFromResponse(JwtResponse response){
        return new JwtResponseResource(
                response.token(),
                response.type(),
                response.id(),
                response.username(),
                response.email(),
                response.Roles()
        );
    }
}
