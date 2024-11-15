package org.example.cornchat_be.domain.user.dto;

import lombok.Builder;
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
    public static class EmailDto{
        String email;
    }

    @Getter
    public static class PhoneNumDto{
        String phoneNum;
    }

    @Getter
    public static class UserIdDto{
        String userId;
    }

    @Getter
    public static class FindPwDto{
        String email;
        String code;
        String password;
    }

    @Getter
    public static class LoginDto{
        String email;
        String password;
    }

    @Getter
    public static class DeleteDto{
        String email;
    }
}
