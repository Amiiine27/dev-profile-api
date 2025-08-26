package com.devanalyzer.dev_profile_api.config;

// IMPORTS : Pour la configuration Spring Security
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
            .oauth2Login(oauth2 -> oauth2.permitAll());
            
        return http.build();
    }
}