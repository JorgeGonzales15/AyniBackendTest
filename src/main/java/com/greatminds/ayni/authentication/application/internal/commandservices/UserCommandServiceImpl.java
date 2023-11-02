package com.greatminds.ayni.authentication.application.internal.commandservices;

import com.greatminds.ayni.authentication.domain.model.aggregates.User;
import com.greatminds.ayni.authentication.domain.model.commands.AuthenticateUserCommand;
import com.greatminds.ayni.authentication.domain.model.commands.CreateUserCommand;
import com.greatminds.ayni.authentication.domain.model.payload.response.JwtResponse;
import com.greatminds.ayni.authentication.domain.model.valueobjects.EmailAddress;
import com.greatminds.ayni.authentication.domain.model.valueobjects.Username;
import com.greatminds.ayni.authentication.domain.services.UserCommandService;
import com.greatminds.ayni.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import com.greatminds.ayni.authentication.infrastructure.security.jwt.JwtUtils;
import com.greatminds.ayni.authentication.infrastructure.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;

    public UserCommandServiceImpl(
            UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public Long handle(CreateUserCommand command) {
        var username = new Username(command.username());
        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("User with " + command.username() + " is already taken!");
        }

        var email = new EmailAddress(command.email());
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email with " + command.email() + " is already taken!" );
        }

        // Create a new User Account:
        User user = new User(
                command.username(),
                command.email(),
                encoder.encode(command.password()),
                command.role()
        );

        userRepository.save(user);

        return user.getId();
    }

    @Override
    public Object handle(AuthenticateUserCommand command) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(command.username(), command.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(
                jwt,
                "Bearer ",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
