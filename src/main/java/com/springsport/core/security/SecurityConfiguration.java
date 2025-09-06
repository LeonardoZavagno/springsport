package com.springsport.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
            User.withUsername("leonardo")
                .password("$2a$12$j0qsaMdhezIaE5SiN2lzOuxUKNaUYD87RmJ08qoGlKg0hdD8BZVQG")
                .roles("USER", "ADMIN")
                .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(mvc.pattern("/")).permitAll()
                .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/users/**")).hasAnyRole("USER", "ADMIN")
                .requestMatchers(mvc.pattern("/api/v1/users/**")).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/", true)
            )
            .logout(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable()); //TODO implement this

        return http.build();
    }
}
