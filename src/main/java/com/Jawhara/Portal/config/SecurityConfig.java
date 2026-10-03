package com.Jawhara.Portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").authenticated() // Protect all admin routes
                        .anyRequest().permitAll()                     // Students can access everything else freely
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")                    // Custom login page
                        .defaultSuccessUrl("/admin/dashboard", true)  // Redirect after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Hardcoded admin user credentials (you can move these to application.yml later if preferred)
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("jawhara2026")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}