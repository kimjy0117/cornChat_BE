package org.example.cornchat_be.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

public class UserResponseDto {

    @Getter
    @Builder
    public static class UserInformDto {
        private String userName;
        private String userId;
        private String statusMessage;
    }
}
