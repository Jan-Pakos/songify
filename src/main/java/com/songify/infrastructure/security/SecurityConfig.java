package com.songify.infrastructure.security;

import com.songify.infrastructure.security.jwt.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

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
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthTokenFilter jwtAuthTokenFilter) throws Exception {
        http.formLogin(c -> c.disable());
        http.httpBasic(c -> c.disable());
        http.csrf(c -> c.disable());
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors(corsConfigurerCustomizer());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/users/register/**").permitAll()
                .requestMatchers("/token/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/artists/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/albums/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/genres/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/songs/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/albums/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/albums/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/artists/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/artists/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/genres/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/genres/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        return http.build();
    }

    public Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return cors -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                corsConfiguration.setAllowedHeaders(List.of("*"));
                return corsConfiguration;
            };
            cors.configurationSource(source);
        };
    }
}
