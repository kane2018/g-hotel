package com.kane.hotel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
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
    public DaoAuthenticationProvider authenticationProvider2() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService2());
        auth.setPasswordEncoder(passwordEncoder2());

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider2());

        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        http.antMatcher("/admin/**").authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin")
                .failureUrl("/admin/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .permitAll()
        .and().csrf().disable();


        return http.build();
    }

}
