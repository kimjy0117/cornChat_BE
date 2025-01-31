package org.example.cornchat_be.util.config;

import jakarta.servlet.http.HttpServletRequest;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.jwt.CustomLogoutFilter;
import org.example.cornchat_be.util.jwt.JWTFilter;
import org.example.cornchat_be.util.jwt.JWTUtil;
import org.example.cornchat_be.util.jwt.LoginFilter;
import org.example.cornchat_be.util.jwt.repository.RefreshRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, RefreshRepository refreshRepository, UserRepository userRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.userRepository = userRepository;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    //암호화를 시켜줌
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {

                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();

                                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173")); //5173번 포트 허용
                                configuration.setAllowedOrigins(Collections.singletonList("http://3.36.151.72:5173")); //5173번 포트 허용
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setMaxAge(3600L);

                                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                                return configuration;
                            }
                        }));

        //csrf disable (jwt방식은 세션을 stateless상태로 관리하기 때문에 csrf에 대한 공격을 방어하지 않아도 된다.)
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식
        http
                .httpBasic((auth) -> auth.disable());

        //인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui/index.html").permitAll() // Swagger 관련 URL 허용
                        .requestMatchers("/api/login/**", "/").permitAll()
                        .requestMatchers("/api/user/deleteUser").authenticated() // 인증 필요
//                        .requestMatchers("/api/user/deleteUser").hasRole("USER") // 인증 필요
                        .requestMatchers("/api/user/profile").authenticated() // 인증 필요
                        .requestMatchers("/api/user/name").authenticated() // 인증 필요
                        .requestMatchers("/api/user/statusMessage").authenticated() // 인증 필요
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/email/**").permitAll() // email 인증 관련 URL 허용
                        .requestMatchers("/api/token/reissue").permitAll()
                        .requestMatchers("/ws/**").permitAll() // WebSocket 엔드포인트 허용
                        .anyRequest().authenticated());

        //JWTFilter를 LoginFilter 앞에 추가하여 JWT토큰을 처리
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //기본인증 필터인 UsernamePasswordAuthenticationFilter를 대신하여 커스텀 로직인 loginFilter로 대체함
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository, userRepository), UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
