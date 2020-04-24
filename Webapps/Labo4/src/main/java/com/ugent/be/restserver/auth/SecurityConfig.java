package com.ugent.be.restserver.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// Two sets of security configurations. We switch between them with the profiles

// No authorization required if "test" profile is active
@Configuration
@Profile("test")
class SecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/**");
    }
}

// Other profile
@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled=true)
@Profile("!test")
class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_USER = "user";
    public static final String USER_PASSWORD = "user";
    public static final String USER_ROLE = "USER";


    /**
     * Define the users.
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(ADMIN_USER).password(passwordEncoder.encode(ADMIN_PASSWORD)).roles(ADMIN_ROLE).and()
                .withUser(USER_USER).password(passwordEncoder.encode(USER_PASSWORD)).roles(USER_ROLE);
    }

    /*
    Configure the security for individual pages
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/admin.html", "/admin.js").hasRole(ADMIN_ROLE)
                .mvcMatchers("/", "/index.html", "/index.js", "/posts.json", "/favicon.ico").permitAll()
                .mvcMatchers("/posts/**").permitAll()
                .mvcMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll().and()
                .headers().frameOptions().disable().and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll().and()
                .csrf().disable();
    }
}