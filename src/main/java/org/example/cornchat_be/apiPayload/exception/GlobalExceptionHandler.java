package org.example.cornchat_be.apiPayload.exception;

import org.example.cornchat_be.apiPayload.code.dto.ErrorDto;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//전역 에러처리
@RestControllerAdvice
public class GlobalExceptionHandler {
    //400 BAD_REQUEST 비즈니스 로직 에러
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e){
        ErrorDto errorResponse = new ErrorDto(e.getMessage(), ErrorStatus._BAD_REQUEST.getCode(), ErrorStatus._BAD_REQUEST.getHttpStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 401 Unauthorized 인증 실패 에러
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(SecurityException e) {
        ErrorDto errorResponse = new ErrorDto(e.getMessage(), ErrorStatus._UNAUTHORIZED.getCode(), ErrorStatus._UNAUTHORIZED.getHttpStatus());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // 403 Forbidden 권한 부족 에러
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleForbiddenException(AccessDeniedException e) {
        ErrorDto errorResponse = new ErrorDto(e.getMessage(), ErrorStatus._FORBIDDEN.getCode(), ErrorStatus._FORBIDDEN.getHttpStatus());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // 409 Conflict 서버와 현재 상태 충돌 에러
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDto> handleConflictException(IllegalStateException e) {
        ErrorDto errorResponse = new ErrorDto(e.getMessage(), ErrorStatus._CONFLICT.getCode(), ErrorStatus._CONFLICT.getHttpStatus());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    //500 INTERNAL_SERVER_ERROR 서버에러
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerException(NullPointerException e){
        ErrorDto errorResponse = new ErrorDto(e.getMessage(), ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // 500 모든 예상하지 못한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleAllUnhandledExceptions(Exception e) {
        ErrorDto errorResponse = new ErrorDto(e.getMessage(), ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException e) {
        ErrorDto errorResponse = e.getErrorStatus().convertErrorDto();
        return ResponseEntity.status(e.getErrorStatus().getHttpStatus()).body(errorResponse);
    }
}
