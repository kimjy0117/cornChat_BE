package org.example.cornchat_be.util;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.jwt.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityUtil  {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new SecurityException("사용자 정보를 불러올 수 없습니다.");
        }

        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            String userEmail = userDetails.getUsername(); // 사용자 이메일 가져오기
            return userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("접속한 사용자 정보를 읽을 수 없습니다."));
        }

        throw new SecurityException("유효하지 않은 사용자 인증 정보입니다.");
    }
}
