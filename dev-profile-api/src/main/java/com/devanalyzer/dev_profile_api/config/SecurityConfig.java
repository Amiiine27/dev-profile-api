package com.devanalyzer.dev_profile_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// ANNOTATION : Cette classe configure Spring Security
@Configuration
public class SecurityConfig {

    // ANNOTATION : Cette méthode crée un Bean Spring
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        // CONFIGURATION : Définir les règles d'accès
        http.authorizeHttpRequests(auth -> auth
                // ENDPOINTS LIBRES : "/" et "/api/test" accessibles sans auth
                .requestMatchers("/", "/api/test").permitAll()
                // AUTRES ENDPOINTS : Authentification obligatoire
                .anyRequest().authenticated()
            )
            // AUTH FORMULAIRE : Permet le login admin/password
            .formLogin(form -> form.permitAll())
            // AUTH OAUTH2 : Permet le login GitHub
            .oauth2Login(oauth2 -> oauth2
                    .defaultSuccessUrl("http://localhost:5173", true)
                    .permitAll())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1)
);
            
            
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
}
}