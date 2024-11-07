package org.example.cornchat_be.apiPayload.code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorDto {
    private final Boolean isSuccess;
    private final String message;
    private final String errorCode;
    private final HttpStatus status;

    public ErrorDto(String message, String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.isSuccess = false;
        this.message = message;
        this.status = status;
    }
}
