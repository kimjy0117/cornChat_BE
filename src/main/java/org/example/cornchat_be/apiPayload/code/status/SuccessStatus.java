package org.example.cornchat_be.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.cornchat_be.apiPayload.code.dto.SuccessDto;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus {
    // 일반적인 응답
    _GET_OK(HttpStatus.OK, "COMMON200", "200 OK"),
    _POST_OK(HttpStatus.CREATED,"COMMON201","201 Created."),
    _DELETE_OK(HttpStatus.ACCEPTED,"COMMON202","삭제되었습니다."),
    _UPDATE_SUCCESS(HttpStatus.NO_CONTENT, "COMMON204", "업데이트 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public <T> SuccessDto<T> convertSuccessDto(T data) {
        return SuccessDto.<T>builder()
                .isSuccess(true)
                .successCode(code)
                .message(message)
                .data(data)
                .httpStatus(httpStatus)
                .build();
    }
}
