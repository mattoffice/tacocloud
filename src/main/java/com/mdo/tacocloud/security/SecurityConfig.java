package com.mdo.tacocloud.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* use AuthenticationManagerBuilder to specify how users will be looked up during authentication */

        // first, perhaps most straightforward, is the in-memory authentication option
        auth.inMemoryAuthentication()
                .withUser("Elliott")
                .password("{noop}either/or")
                .authorities("ROLE_USER")
                .and()
                .withUser("Lionel")
                .password("{noop}goat")
                .authorities("ROLE_USER");
    }
}
