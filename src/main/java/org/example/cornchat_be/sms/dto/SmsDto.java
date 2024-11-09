package org.example.cornchat_be.sms.dto;

import lombok.Getter;

public class SmsDto {

    @Getter
    public static class EmailDto{
        public String email;
    }

    @Getter
    public static class VerifyCodeDto{
        public String email;
        public String code;
    }
}
