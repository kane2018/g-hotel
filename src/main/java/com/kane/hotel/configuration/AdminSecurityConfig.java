package com.kane.hotel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class AdminSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService2() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager2(
            HttpSecurity http) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService2())
                .passwordEncoder(passwordEncoder2())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/ads/**/book").hasRole("USER")
                .antMatchers("/register", "/", "/ads/**", "/user/**").permitAll();
        http.
                antMatcher("/admin/**").authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .permitAll()
                .defaultSuccessUrl("/admin")
                .failureUrl("/admin/login?error=true")
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .permitAll()
        .and().csrf().disable()
        .sessionManagement();


        return http.build();
    }

}
