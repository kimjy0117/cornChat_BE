package org.example.cornchat_be.util.config;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.util.interceptor.WebSocketHandshakeInterceptor;
import org.example.cornchat_be.util.jwt.JWTUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@AllArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JWTUtil jwtUtil;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub", "/topic", "/queue");  // 클라이언트가 구독할 브로커 경로
        config.setApplicationDestinationPrefixes("/pub");  // 클라이언트가 보낼 메시지 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")// 클라이언트의 연결 엔드포인트
//                .addInterceptors(new WebSocketHandshakeInterceptor(jwtUtil))
                .setAllowedOriginPatterns("*") // 프론트엔드 오리진 허용
                .withSockJS();
    }
}