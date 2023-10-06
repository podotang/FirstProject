package com.teamntp.firstproject.security.config;

import com.teamntp.firstproject.security.handler.MemberLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // Spring Configuration file 선언
@EnableWebSecurity // 내부적으로 SecurityFilterChain 동작, URL 필터가 적용
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() { // BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // filterChain 설정

        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // 정적 리소스
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/plugin/**", "/vendor/**").permitAll()
                        // URL 설정
                        .requestMatchers("/member/**").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                // 세션 방식 로그인
                .formLogin((login) -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .loginProcessingUrl("/login")
                        .usernameParameter("loginId")
                        .successHandler(memberLoginSuccessHandler())
                        .permitAll()
                )
                // 로그아웃
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll())
                // OAuth2
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
//                        .loginProcessingUrl("/loginProc")
                        .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
                                .baseUri("/oauth2/authorize"))
                        .successHandler(memberLoginSuccessHandler()))
                // 스프링 시큐리티는 사이트의 콘텐츠가 다른 사이트에 포함되지 않도록 하기 위해 X-Frame-Options 헤더값을 사용하여 이를 방지한다.
                // Click Jacking 공격을 막기위해 사용함
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 스프링 시큐리티의 경우 기본적으로 CSRF 옵션을 제공함
                // 해당 옵션을 끄고 싶은 경우 csrf.disable()
                // csrfTokenRepository: 'XSRF-TOKEN' 쿠키에 CSRF 토큰을 유지
                // JS 와 함께 사용하는 경우 withHttpOnlyFalse() 활성화
                // 보통 CSRF 옵션은 POST, DELETE, UPDATE 방식에 적용함
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        ;

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public MemberLoginSuccessHandler memberLoginSuccessHandler() {
        return new MemberLoginSuccessHandler(passwordEncoder());
    }
}
