package com.example.spring.Security_demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class secureConfig {

    @Autowired
    private UserDetailsService userDetailService;

    @Bean
    public AuthenticationProvider authProvider(){

        DaoAuthenticationProvider dataauth=new DaoAuthenticationProvider();
        dataauth.setUserDetailsService(userDetailService);
        dataauth.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return  dataauth;

    }
    @Bean
    public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {

        http.csrf(customizer->customizer.disable());
        http.authorizeRequests(request->request.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        return http.build();
    }

//    @Bean
//    public UserDetailsService userdetailsservice(){
//        UserDetails user= User.withDefaultPasswordEncoder().username("rajesh").password("user123").roles("USER").build();
//
//        UserDetails admin=User.withDefaultPasswordEncoder().username("mythresh").password("admin123").roles("ADMIN").build();
//
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }
}
