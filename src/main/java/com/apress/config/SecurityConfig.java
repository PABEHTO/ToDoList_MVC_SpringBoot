package com.apress.config;

import com.apress.entity.User;
import com.apress.entity.UserRole;
import com.apress.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(d -> d.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/registration", "/login?error")
                        .permitAll()
                        .requestMatchers("/account/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                        .requestMatchers("/resources/**", "/css/**", "/favicon.ico", "/.well-known/**").permitAll())
                .formLogin(fl -> fl
                        .loginPage("/login")
                        .permitAll()
                        .usernameParameter("login")
                        .defaultSuccessUrl("/account")
                        .failureUrl("/login?error")
                        .loginProcessingUrl("/login"))
                .logout(l -> l
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/account/**").authenticated()
                        .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository
                        .findByLoginIgnoreCase(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User with the login=" + username + " not found"));
                Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().getAuthority());
                return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
            }
        };
    }
}
