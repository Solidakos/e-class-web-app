package com.example.eclasswebapp.security;

import javax.sql.DataSource;

import com.example.eclasswebapp.mysql.user.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
     
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .antMatchers("/portfolio").hasAnyAuthority("admin", "professor", "student")
                        .antMatchers("/navbar").authenticated()
                        .antMatchers("/insert").hasAnyAuthority("professor", "admin")
                        .antMatchers("/courses").authenticated()
                        .antMatchers("/view").authenticated()
                        .antMatchers("/addcourse").hasAnyAuthority("professor", "admin")
                        .antMatchers("/changepass").hasAnyAuthority("admin", "professor", "student")
                        .antMatchers("/changename").hasAnyAuthority("admin", "professor", "student")
                        .antMatchers("/process_register").permitAll()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/").permitAll()
                        .anyRequest().permitAll())
                .formLogin(login -> login.loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/portfolio")
                        .permitAll())
                .logout(logout -> logout
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                        .permitAll());
    }
     
}
