package com.zhaoming.web.manage.config;

import com.zhaoming.base.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * @author zm
 */
@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private AccessDeniedHandler myAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return PasswordUtil.encrypt(String.valueOf(charSequence));
            }
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return PasswordUtil.encrypt(String.valueOf(charSequence)).equals(s);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .and()
                    .formLogin()
                    .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll()
                        .failureHandler(myAuthenticationFailureHandler)
                        .successHandler(myAuthenticationSuccessHandler)
                .and()
                    .authorizeRequests()
                    .antMatchers("/login*","/client/manager/login","/fonts/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .csrf()
                    .disable()
                .exceptionHandling()
                    .accessDeniedHandler(myAccessDeniedHandler);

    }
}
