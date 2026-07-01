package com.example.aguacontrol.security;

import com.example.aguacontrol.serviceImpl.ACUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ACUserDetailsService serv;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login", "/signup/**", "/css/**", "/js/**", "/img/**"
                        ).permitAll()
                        .requestMatchers("/surface/**").hasAuthority(Auths.DASHBOARD_CLIENT)
                        .requestMatchers("/business/home").hasAuthority(Auths.DASHBOARD_WORKER)
                        .requestMatchers("/business/clients/**").hasAuthority(Auths.CLIENTS_MANAGE)
                        .requestMatchers("/business/clients").hasAuthority(Auths.CLIENTS_VIEW)
                        .requestMatchers("/business/orders/**").hasAuthority(Auths.ORDERS_MANAGE)
                        .requestMatchers("/business/orders").hasAuthority(Auths.ORDERS_VIEW)
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("identifier")
                        .passwordParameter("password")
                        .successHandler((req, res, auth) -> {
                            //SUCCESS HANDLER
                            if (auth.getAuthorities().stream().anyMatch(a ->
                                    a.getAuthority().equals(Auths.DASHBOARD_WORKER))) {
                                res.sendRedirect("/business/home");
                            } else {
                                res.sendRedirect("/home");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(serv)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}