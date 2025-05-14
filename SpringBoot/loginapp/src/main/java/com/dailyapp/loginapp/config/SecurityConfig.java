package com.dailyapp.loginapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Bean para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de seguridad adaptada para login con fetch
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF por simplicidad en apps SPA
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/home.html", 
                    "/login.html", 
                    "/registro.html",
                    "/dashboard.html",
                    "/ingresos.html",
                    "/auth/registro", 
                    "/auth/login", 
                    "/auth/usuario",// importante para obtener usuario logueado
                    "/css/**", 
                    "/js/**", 
                    "/img/**", 
                    "/images/**", 
                    "/fonts/**"
                ).permitAll()
                .requestMatchers("/dashboard.html", "/dashboard/**").authenticated()
                .anyRequest().permitAll()
            )
            // ✅ No usar .formLogin porque usas JavaScript con fetch
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/login.html")
                .permitAll()
            );

        return http.build();
    }
}
