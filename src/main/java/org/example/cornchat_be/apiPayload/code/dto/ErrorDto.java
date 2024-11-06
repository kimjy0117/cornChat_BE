package org.example.cornchat_be.apiPayload.code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private final Boolean isSuccess;
    private final String message;
    private final int status;

    public ErrorDto(String message, HttpStatus status) {
        this.isSuccess = false;
        this.message = message;
        this.status = status.value();
    }
}
