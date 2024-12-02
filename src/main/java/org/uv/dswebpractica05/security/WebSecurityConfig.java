/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.uv.dswebpractica05.repositories.UserRepository;

/**
 *
 * @author juan
 */
@Configuration
public class WebSecurityConfig {
    
    private final UserDetailsServiceImp udi;
    private final JWTUtils jwtUtils;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final UserRepository usuario;
    
    public WebSecurityConfig(UserDetailsServiceImp udi, JWTUtils jwtUtils,
            JWTAuthorizationFilter jwtAuthorizationFilter, UserRepository usuario){
        this.udi=udi;
        this.jwtUtils=jwtUtils;
        this.jwtAuthorizationFilter=jwtAuthorizationFilter;
        this.usuario = usuario;
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception{
        
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtUtils, usuario);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");
        
        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth->{
                    auth.regexMatchers("/users/createAccount").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    session.sessionFixation().migrateSession();
                })
                .httpBasic()
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udi)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}
