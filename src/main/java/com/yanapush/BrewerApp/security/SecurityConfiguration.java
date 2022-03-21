package com.yanapush.BrewerApp.security;

//import com.yanapush.BrewerApp.user.UserServiceImpl;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.sql.DataSource;
//import java.util.Arrays;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @NonNull
//    private DataSource dataSource;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    private JWTTokenHelper jWTTokenHelper;
//
//    @Autowired
//    private AuthenticationEntryPoint authenticationEntryPoint;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

////        auth.userDetailsService((UserDetailsService)userService).passwordEncoder(passwordEncoder());
//
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
////    @CrossOrigin
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint).and()
//                .authorizeRequests((request) -> request.antMatchers("/h2-console/**", "/api/v1/auth/login").permitAll()
//                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
//                .addFilterBefore(new JWTAuthenticationFilter(userService, jWTTokenHelper),
//                        UsernamePasswordAuthenticationFilter.class);
//
//        http.csrf().disable().cors().and().headers().frameOptions().disable();
//
//    }
//}

import com.yanapush.BrewerApp.user.UserServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JWTTokenHelper jWTTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("SELECT u.username, a.authority " + "FROM authorities a, users u "
                        + "WHERE u.username = ? " + "AND u.user_id = a.user_id");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @CrossOrigin
    protected void configure(HttpSecurity http) throws Exception {


        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests((request) -> request.antMatchers("/h2-console/**", "/api/v1/auth/login").permitAll()
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter(userService, jWTTokenHelper),
                        UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().cors().and().headers().frameOptions().disable();
    }

}