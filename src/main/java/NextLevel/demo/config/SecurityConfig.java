package NextLevel.demo.config;

import NextLevel.demo.oauth.OAuthFailureHandler;
import NextLevel.demo.oauth.OAuthSuccessHandler;
import NextLevel.demo.oauth.SocialLoginService;
import NextLevel.demo.repository.UserDetailRepository;
import NextLevel.demo.service.UserService;
import NextLevel.demo.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final SocialLoginService socialLoginService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        OAuthSuccessHandler oAuthSuccessHandler = new OAuthSuccessHandler(userService);
        OAuthFailureHandler oAuthFailureHandler = new OAuthFailureHandler();

        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (필요에 따라 활성화 가능)
            .formLogin(form -> form.disable()) // 폼 로그인 비활성화
            .httpBasic(httpBasic -> httpBasic.disable()) // 기본 로그인 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/public/**").permitAll() // 특정 경로만 허용
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            )

            .oauth2Login((social) -> social
                .userInfoEndpoint(s -> s.userService(socialLoginService))
                // .authorizedClientRepository()
                .successHandler(oAuthSuccessHandler)
                .failureHandler(oAuthFailureHandler)
            )

            //.addFilterBefore(, )

//            .exceptionHandling((exceptions) -> exceptions
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                .accessDeniedHandler(new CustomAccessDeniedHandler()))

            ;

        return http.build();
    }
}
