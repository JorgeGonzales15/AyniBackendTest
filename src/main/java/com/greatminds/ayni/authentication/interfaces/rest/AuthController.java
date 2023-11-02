package com.greatminds.ayni.authentication.interfaces.rest;

import com.greatminds.ayni.authentication.domain.model.payload.response.JwtResponse;
import com.greatminds.ayni.authentication.domain.model.queries.GetUserByIdQuery;
import com.greatminds.ayni.authentication.domain.services.UserCommandService;
import com.greatminds.ayni.authentication.domain.services.UserQueryService;
import com.greatminds.ayni.authentication.interfaces.rest.resources.AuthenticateUserResource;
import com.greatminds.ayni.authentication.interfaces.rest.resources.CreateUserResource;
import com.greatminds.ayni.authentication.interfaces.rest.transform.AuthenticateUserCommandFromResourceAssembler;
import com.greatminds.ayni.authentication.interfaces.rest.transform.AuthenticationResourceFromAuthenticationResponseAssembler;
import com.greatminds.ayni.authentication.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.greatminds.ayni.authentication.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "Authentication", description = "Authentication endpoints")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserQueryService userQueryService;
    @Autowired
    UserCommandService userCommandService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticateUserResource resource){
        var authenticateUserCommand = AuthenticateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var jwtResponse = userCommandService.handle(authenticateUserCommand);

        if (jwtResponse == null){
            return ResponseEntity.badRequest().build();
        }

        var authenticatedResource = AuthenticationResourceFromAuthenticationResponseAssembler.toResourceFromResponse((JwtResponse) jwtResponse);
        return new ResponseEntity<>(authenticatedResource, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserResource resource){
        var createUserCommand = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var userId = userCommandService.handle(createUserCommand);
        if (userId == 0L){
            return ResponseEntity.badRequest().build();
        }

        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}
