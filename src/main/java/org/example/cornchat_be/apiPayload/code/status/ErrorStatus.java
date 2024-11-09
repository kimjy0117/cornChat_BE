package org.example.cornchat_be.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.cornchat_be.apiPayload.code.dto.ErrorDto;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorStatus {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _CONFLICT(HttpStatus.CONFLICT,"COMMON409","잘못된 입력입니다."),

    // password 에러
    _ALREADY_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "PASSWORD4001", "기존 비밀번호와 동일합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ErrorDto convertErrorDto(){
        return ErrorDto.builder()
                .status(httpStatus)
                .errorCode(code)
                .message(message)
                .isSuccess(false)
                .build();
    }
}