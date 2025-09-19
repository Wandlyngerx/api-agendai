package com.app.agendai.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf-> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/agendai/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/agendai/auth/register/client").permitAll()
                        .requestMatchers(HttpMethod.POST, "/agendai/auth/register/enterprise").permitAll()
                        .requestMatchers(HttpMethod.GET, "/agendai/client/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/agendai/reservation/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/agendai/reservation/{id}").hasRole("USER") //busca reserva pelo id do usuario
                        .requestMatchers(HttpMethod.GET, "/agendai/reservation/service/{id}").hasRole("ADMIN")// buscca reserva pelo id de um servico
                        .requestMatchers(HttpMethod.POST, "/agendai/service").hasRole("ADMIN")//crie um servico
                        .requestMatchers(HttpMethod.PUT, "/agendai/service/update/{id}").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class )
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
