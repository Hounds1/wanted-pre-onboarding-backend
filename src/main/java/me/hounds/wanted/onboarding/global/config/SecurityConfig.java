package me.hounds.wanted.onboarding.global.config;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.global.jwt.TokenProvider;
import me.hounds.wanted.onboarding.global.jwt.handler.JwtAccessDeniedHandler;
import me.hounds.wanted.onboarding.global.jwt.handler.JwtAuthenticationEntryPoint;
import me.hounds.wanted.onboarding.global.security.CustomAuditorAware;
import me.hounds.wanted.onboarding.global.security.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.web.cors.CorsConfiguration.*;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private static final String PUBLIC = "/api/v1/public/**";
    private static final String ADMIN = "/api/v1/admin/**";
    private static final String NORMAL = "/api/v1/**";
    private static final String API_DOCS = "/docs/**";
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new CustomAuditorAware();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        /**
         * 1. csrf 비활성화
         * 2. Spring Security에서 제공하는 formLogin 비활성화
         * 3. cors 정책 변경 (모든 외부 요청으로 부터 개방)
         * 4. ExceptionHandler 설정
         * 5. 외부 사이트에서 iframe 태그를 통해 랜더링 할 수 없도록 조치
         * 6. public 키워드를 가진 url은 전체 개방 및 모든 ROLE에게 접근 허용(임시)
         */
        security.csrf()
                .disable();

        security.formLogin()
                .disable();

        security.cors()
                .configurationSource(corsConfigurationSource());

        security.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        security.authenticationProvider(new CustomAuthenticationProvider());

        security.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security.headers()
                .frameOptions().sameOrigin();

        security.authorizeHttpRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(API_DOCS).permitAll()
                .antMatchers(ADMIN).hasAnyRole("ADMIN")
                .antMatchers(NORMAL).hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated();

        security.apply(new JwtSecurityConfig(tokenProvider));

        return security.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern(ALL);
        configuration.addAllowedHeader(ALL);
        configuration.addAllowedMethod(ALL);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
