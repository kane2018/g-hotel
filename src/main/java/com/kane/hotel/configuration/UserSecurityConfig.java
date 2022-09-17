package com.kane.hotel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class UserSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeRequests().antMatchers("/ads/**/book", "/ads/new", "/ads/**/edit").hasRole("USER");

        http.authorizeRequests().antMatchers("/register", "/", "/ads/**", "/user/**").permitAll();

        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll()

        .and().csrf().disable();


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/css/**", "/js/**", "/error");
    }
}
