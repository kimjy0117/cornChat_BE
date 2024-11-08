package org.example.cornchat_be.apiPayload.exception;

import lombok.Getter;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public CustomException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

}