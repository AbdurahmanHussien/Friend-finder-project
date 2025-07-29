package com.springboot.friend_finder.config;

import com.springboot.friend_finder.service.auth.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(auth -> auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails)
                .map(auth -> ((CustomUserDetails) auth.getPrincipal()).getUser().getUserDetails().getName())
                .or(() -> Optional.of("anonymousUser"));
    }

}
