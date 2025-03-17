package com.YCDxh.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/swagger-ui/**", "/v2/api-docs", "/webjars/**", "/**", "/swagger-resources/**").permitAll() // 添加Swagger白名单
                        .antMatchers("/api/chapter/create-chapter").hasRole("teacher")
                        .anyRequest().authenticated()

                )
                .httpBasic() // 如果需要基本认证
                .and()
                .csrf().disable(); // 根据需求决定是否禁用CSRF
        return http.build();
    }

}