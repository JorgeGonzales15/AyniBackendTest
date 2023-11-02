package com.greatminds.ayni.authentication.infrastructure.security.services;

import com.greatminds.ayni.authentication.domain.model.aggregates.User;
import com.greatminds.ayni.authentication.domain.model.valueobjects.Username;
import com.greatminds.ayni.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(new Username(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
