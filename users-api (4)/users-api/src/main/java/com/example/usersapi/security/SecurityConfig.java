package com.example.usersapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // enables @PreAuthorize on controller/service methods
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AppUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AppUserDetailsService userDetailsService,
            JwtAuthenticationEntryPoint authenticationEntryPoint,
            JwtAccessDeniedHandler accessDeniedHandler
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt with default strength (10)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // not needed for stateless token-based APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint) // 401
                        .accessDeniedHandler(accessDeniedHandler)           // 403
                )
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/**").permitAll()

                        // Public hotel browsing (GET endpoints)
                    .requestMatchers(HttpMethod.GET, "/api/hotels").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/hotels/{id}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/hotels/city/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/hotels/country/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/hotels/owner/**").permitAll()

                        // Public room browsing (GET endpoints)
                    .requestMatchers(HttpMethod.GET, "/api/rooms/{id}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/rooms/hotel/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/rooms/search").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/bookings/availability/check").permitAll()

                        // Hotel management (create, edit, delete require auth)
                    .requestMatchers(HttpMethod.POST, "/api/hotels").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/hotels/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/hotels/**").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/hotels/my-hotels").authenticated()

                        // Room management (create, edit, delete require auth)
                    .requestMatchers(HttpMethod.POST, "/api/rooms").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/rooms/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/rooms/**").authenticated()

                        // Booking endpoints (require auth)
                        .requestMatchers("/api/bookings/**").authenticated()

                        // Admin-only user management
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Any authenticated user can access their own profile
                        .requestMatchers("/api/profile/**").authenticated()

                        // Everything else requires authentication by default
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
