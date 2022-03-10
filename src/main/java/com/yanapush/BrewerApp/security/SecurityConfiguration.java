//package com.yanapush.BrewerApp.security;
//
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.sql.DataSource;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @NonNull
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("SELECT username, password, enabled from users where username = ?")
//                .authoritiesByUsernameQuery("SELECT u.username, a.authority " + "FROM authorities a, users u "
//                        + "WHERE u.username = ? " + "AND u.user_id = a.user_id");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin();
//        http.authorizeRequests()
//        .antMatchers("/login", "/register").permitAll()
//        .antMatchers("/*").hasAnyRole("ADMIN", "USER");
//    }
//}