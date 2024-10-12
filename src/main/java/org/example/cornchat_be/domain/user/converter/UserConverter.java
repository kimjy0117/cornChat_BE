package org.example.cornchat_be.domain.user.converter;

import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.example.cornchat_be.domain.user.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class UserConverter {

    public static User createUser(UserRequestDto.JoinDto request, BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNum(request.getPhoneNum())
                .userId(request.getUserId())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
