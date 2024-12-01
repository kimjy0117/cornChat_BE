package org.example.cornchat_be.apiPayload.code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class ErrorDto {
    private final Boolean isSuccess = false;
    private final String message;
    private final String errorCode;
    private final HttpStatus status;
}
