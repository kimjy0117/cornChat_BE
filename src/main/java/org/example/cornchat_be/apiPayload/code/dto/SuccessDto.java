package org.example.cornchat_be.apiPayload.code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class SuccessDto<T> {
    private final boolean isSuccess;  // 성공 여부
    private final String message;     // 성공 메시지
    private final String successCode;        // 성공 코드
    private final HttpStatus httpStatus; // HTTP 상태 코드
    private final T data;             // 응답 데이터


    public SuccessDto(String message, String successCode, HttpStatus httpStatus, T data) {
        this.isSuccess = true;
        this.message = message;
        this.successCode = successCode;
        this.httpStatus = httpStatus;
        this.data = data;
    }
}
