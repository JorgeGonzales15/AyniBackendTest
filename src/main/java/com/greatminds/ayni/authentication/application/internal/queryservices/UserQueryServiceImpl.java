package com.greatminds.ayni.authentication.application.internal.queryservices;

import com.greatminds.ayni.authentication.domain.model.aggregates.User;
import com.greatminds.ayni.authentication.domain.model.queries.GetUserByIdQuery;
import com.greatminds.ayni.authentication.domain.model.queries.GetUserByUsernameQuery;
import com.greatminds.ayni.authentication.domain.services.UserQueryService;
import com.greatminds.ayni.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return this.userRepository.findByUsername(query.username());
    }
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return this.userRepository.findById(query.id());
    }

}
