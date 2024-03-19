package com.webapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/rutube.ru/admin/moderators").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/rutube.ru/movies").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers(HttpMethod.POST, "/rutube.ru/genres").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/rutube.ru/cards").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .requestMatchers(HttpMethod.GET, "/rutube.ru/movies").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .requestMatchers(HttpMethod.DELETE, "/rutube.ru/video/*").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers(HttpMethod.POST, "/rutube.ru/video/*").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .requestMatchers("/rutube.ru/registration", "/rutube.ru/authentication").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
