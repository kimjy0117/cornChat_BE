package org.example.cornchat_be.domain.user.dto;

import lombok.Getter;

public class UserRequestDto {
    @Getter
    public static class JoinDto{
        String userName;
        String email;
        String phoneNum;
        String userId;
        String password;
    }

    @Getter
    public static class LoginDto{
        String email;
        String password;
    }

}
