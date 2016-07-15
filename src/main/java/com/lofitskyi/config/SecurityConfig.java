package com.lofitskyi.config;

import com.lofitskyi.util.RoleBasedAuthenticationSuccessHandler;
import com.lofitskyi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private static final int TEN_MINUTES = 600;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(authenticationService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin**", "/change**").hasRole("admin")
                .antMatchers("/user").hasRole("user")
                .antMatchers("/home", "/login").anonymous()
                .anyRequest().permitAll()
                .and();

        http.formLogin()
                    .loginPage("/login")
                    .successHandler(new RoleBasedAuthenticationSuccessHandler())
                    .failureUrl("/login?error=t")
                .and()
                    .rememberMe()
                    .tokenValiditySeconds(TEN_MINUTES)
                    .key("gKey");

        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true);
    }
}
