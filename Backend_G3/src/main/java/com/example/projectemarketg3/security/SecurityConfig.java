package com.example.projectemarketg3.security;

import com.example.projectemarketg3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationEntryPointCustom authenticationEntryPointCustom;

    @Autowired
    private AuthorizationFilterCustom authorizationFilterCustom;

    @Autowired
    private AccessDeniedHandlerCustom accessDeniedHandlerCustom;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                    .antMatchers("/api/auth/**", "/", "/api/test/**","/api/v1/**").permitAll()
//                    .antMatchers("/api/admin/**","/api/v1/admin/**").hasRole("ADMIN")
//                    .antMatchers("/api/user/carts","/api/user/order-bill/**","/api/user/quantity-detail/**",
//                            "/api/user/detail-delete/**","/api/user/rating-product/**","/api/user/rating-user/**",
//                            "/api/user/update-pass").hasRole("USER")
//                    .anyRequest().authenticated()

                .antMatchers("/*").permitAll()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPointCustom)
                    .accessDeniedHandler(accessDeniedHandlerCustom)
                .and()
                    .logout()
                    .logoutUrl("/api/auth/logout")
                    .deleteCookies("MY_SESSION")
                    .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                    .permitAll()
//                .and()
//                    .addFilterBefore(authorizationFilterCustom, UsernamePasswordAuthenticationFilter.class)
        ;
    }

}