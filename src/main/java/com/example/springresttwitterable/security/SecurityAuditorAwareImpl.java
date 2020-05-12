package com.example.springresttwitterable.security;

import com.example.springresttwitterable.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Created on 2020-05-11
 *
 * @author generatorr
 */
public class SecurityAuditorAwareImpl implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        User currentUser = ((User) authentication.getPrincipal());
        return Optional.of(currentUser);
    }
}
