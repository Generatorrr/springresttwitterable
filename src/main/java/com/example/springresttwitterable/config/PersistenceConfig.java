package com.example.springresttwitterable.config;

import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.security.SecurityAuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created on 2020-05-11
 *
 * @author generatorr
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfig {

    @Bean
    public AuditorAware<User> auditorAware(){
        return new SecurityAuditorAwareImpl();
    }
}
