package com.apoteksehatsentosa.sisteminformasiapotek.Auditable;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        // Just return a string representing the username
        return Optional.ofNullable("Ibrahim").filter(s -> !s.isEmpty());
    }
}
