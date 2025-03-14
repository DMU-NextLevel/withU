package NextLevel.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (필요에 따라 활성화 가능)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll() // 특정 경로만 허용
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            )
            .formLogin(form -> form.disable()) // 폼 로그인 비활성화
            .httpBasic(httpBasic -> httpBasic.disable()) // 기본 로그인 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 사용 안함

        return http.build();
    }
}
