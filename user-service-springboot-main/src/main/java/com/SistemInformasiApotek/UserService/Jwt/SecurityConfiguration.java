package com.SistemInformasiApotek.UserService.Jwt;

import jakarta.servlet.Filter;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Nonaktifkan CSRF (Cross-Site Request Forgery) untuk aplikasi yang menggunakan API stateless
                .csrf().disable()
                // Konfigurasi aturan otorisasi untuk berbagai endpoint (URI)
                .authorizeHttpRequests()
                .requestMatchers("/user/register", "/user/login").permitAll()
                .requestMatchers("/user/tes-admin").hasAuthority("ADMIN")
                .requestMatchers("/user/tes-cashier").hasAuthority("CASHIER")
                .requestMatchers("/user/list").hasAuthority("ADMIN")
                .requestMatchers("/user/search").hasAuthority("ADMIN")
                .requestMatchers("/user/delete").hasAuthority("ADMIN")
                .requestMatchers("/user/update").hasAuthority("ADMIN")
                .requestMatchers("/user/update-role").hasAuthority("ADMIN")
                .requestMatchers("/user/ubahrole/{nik}").hasAuthority("ADMIN")
                // Semua permintaan yang tidak sesuai dengan aturan di atas memerlukan autentikasi
                .anyRequest().authenticated()
                // Konfigurasi kebijakan manajemen sesi, dalam hal ini STATELESS artinya aplikasi akan menggunakan JWT dan tidak menyimpan status sesi di server
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // Konfigurasi authentication provider yang digunakan untuk autentikasi pengguna
                .and()
                .authenticationProvider(authenticationProvider)
                // Tambahkan JwtAuthenticationFilter sebelum UsernamePasswordAuthenticationFilter untuk memproses autentikasi berdasarkan token JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // Mengembalikan objek SecurityFilterChain yang telah dikonfigurasi
        return httpSecurity.build();
    }
}
