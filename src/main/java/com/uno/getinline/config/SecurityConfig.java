package com.uno.getinline.config;

import com.uno.getinline.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder auth,
            PasswordEncoder passwordEncoder,
            AdminService adminService
    ) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 해당 패턴은 모두 허용
                .antMatchers("/", "/evnets/**", "/places/**")
                    .permitAll()
                // 그 외 나머지는 인증
                .anyRequest()
                    .authenticated()
                .and()
                // 로그인, 로그아웃 페이지는 모두 허용
                .formLogin()
                    .permitAll()
                    .loginPage("/login")
                    .defaultSuccessUrl("/admin/places")
                .and()
                .logout()
                    .permitAll()
                    .logoutUrl("/logout")
                // 로그아웃 성공하면 루트로
                    .logoutSuccessUrl("/");
    }
}
