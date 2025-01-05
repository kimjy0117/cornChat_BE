package org.example.cornchat_be.util.interceptor;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.util.jwt.JWTUtil;
import org.example.cornchat_be.util.principal.CustomPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    private final JWTUtil jwtUtil;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        // Authorization 헤더에서 토큰 추출
        List<String> authHeaders = request.getHeaders().get("Authorization");
        if(authHeaders == null){
            return false;
        }
        System.out.println(authHeaders.get(0));
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String accessToken = authHeaders.get(0).replace("Bearer ", "");

            // 토큰 만료 여부 확인
            if (jwtUtil.isExpired(accessToken)) {
                throw new CustomException(ErrorStatus._EXPIRED_ACCESS_TOKEN);
            }

            // 토큰이 access인지 확인 (발급시 페이로드에 명시)
            String category = jwtUtil.getCategory(accessToken);
            if (!category.equals("access")) {
                throw new CustomException(ErrorStatus._IS_NOT_ACCESS_TOKEN);
            }

            String userId = jwtUtil.getUserId(accessToken);
            attributes.put("user", new CustomPrincipal(userId));
            return true;
        }
        return false; // 인증 실패 시 연결 거부
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
    }
}
