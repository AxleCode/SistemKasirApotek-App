package com.apoteksehatsentosa.sisteminformasiapotek;

import com.apoteksehatsentosa.sisteminformasiapotek.Auditable.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SistemInformasiApotekApplication {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {

		SpringApplication.run(SistemInformasiApotekApplication.class, args);
	}

}
