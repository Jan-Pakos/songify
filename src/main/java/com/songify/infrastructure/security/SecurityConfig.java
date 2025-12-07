package com.songify.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        var user1 = User.withUsername("user1").password("password").roles("USER").build();
        var user2 = User.withUsername("user2").password("password").roles("USER", "ADMIN").build();
        manager.createUser(user1);
        manager.createUser(user2);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/users/register/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/songs/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/artists/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/albums/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/genres/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH,"/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/songs/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        return http.build();
    }

}
