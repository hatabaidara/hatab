package com.shaoume.config;
import com.shaoume.security.UserDetailsServiceImpl;
import com.shaoume.security.jwt.JwtAuthEntryPoint;
import com.shaoume.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;
@Configuration @EnableWebSecurity @EnableMethodSecurity @RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final UserDetailsServiceImpl userDetailsService;
    @Value("${app.cors.allowed-origins}") private String allowedOrigins;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(c->c.configurationSource(corsConfigurationSource()))
            .exceptionHandling(e->e.authenticationEntryPoint(jwtAuthEntryPoint))
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a
                    .requestMatchers(
                            "/",
                            "/index.html",
                            "/login.html",
                            "/register.html",
                            "/pages/**",
                            "/assets/**",
                            "/auth/**",
                            "/api/v1/auth/**",
                            "/api/register",
                            "/api/login",
                            "/api/auth/register",
                            "/api/health",
                            "/api/auth/login",
                            "/api/categories",
                            "/api/products/**",
                            "/api/categories/**",
                            "/api/upload/**",
                            "/uploads/**",
                            "/favicon.ico",
                            "/favicon.png",
                            "/sw.js",
                            "/manifest.json",
                            "/offline.html"
                    ).permitAll()
                .requestMatchers(HttpMethod.GET,"/products/**","/categories/**","/reviews/product/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/orders/admin/**").hasRole("ADMIN")
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/products/**","/categories/**").hasAnyRole("ADMIN","SELLER")
                .requestMatchers(HttpMethod.PUT,"/products/**","/categories/**").hasAnyRole("ADMIN","SELLER")
                .requestMatchers(HttpMethod.DELETE,"/products/**","/categories/**").hasAnyRole("ADMIN","SELLER")
                .requestMatchers("/wave/**").hasAnyRole("SELLER","ADMIN")
                .requestMatchers("/merchants/admin/**").hasRole("ADMIN")
                .requestMatchers("/merchants/**").hasAnyRole("SELLER","ADMIN","USER")
                .anyRequest().authenticated())
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type","Accept"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p=new DaoAuthenticationProvider();
        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception { return c.getAuthenticationManager(); }
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(12); }
}
