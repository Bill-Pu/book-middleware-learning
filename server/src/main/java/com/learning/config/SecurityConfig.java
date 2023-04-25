package com.learning.config;

import com.learning.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Configuration
public class SecurityConfig  {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    //配置过滤
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()//关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭session
                .and()
                .authorizeRequests(auth ->
                        auth.antMatchers("/user/login").permitAll()
                                .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class).build();
//                .userDetailsService(userDetailsService).build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}