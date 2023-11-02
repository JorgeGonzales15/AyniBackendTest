package com.greatminds.ayni.authentication.domain.services;


import com.greatminds.ayni.authentication.domain.model.aggregates.User;
import com.greatminds.ayni.authentication.domain.model.queries.GetUserByIdQuery;
import com.greatminds.ayni.authentication.domain.model.queries.GetUserByUsernameQuery;

import java.util.Optional;


public interface UserQueryService {
    Optional<User> handle(GetUserByUsernameQuery query);
    Optional<User> handle(GetUserByIdQuery query);

}
