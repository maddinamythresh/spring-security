package com.example.spring.Security_demo.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class secureConfig {

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private jwtFilter jwtFilter;


    @Bean
    public AuthenticationProvider authProvider(){

        DaoAuthenticationProvider dataauth=new DaoAuthenticationProvider();
        System.out.println("Dao");
        dataauth.setUserDetailsService(userDetailService);
       dataauth.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return  dataauth;

    }
    @Bean
    public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
        System.out.println("First");
        http.csrf(customizer->customizer.disable());
        http.authorizeRequests(request->request.requestMatchers("login").permitAll().anyRequest().authenticated());
        //http.formLogin(Customizer.withDefaults());
        System.out.println("Filter chain");
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

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
