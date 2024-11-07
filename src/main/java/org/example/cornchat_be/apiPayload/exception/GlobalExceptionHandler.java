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
    //400 BAD_REQUEST 비즈니스 로직 에러 (비즈니스 로직에서 응답을 선택)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e, ErrorStatus errorStatus){
        ErrorDto errorResponse = errorStatus.convertErrorDto();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 401 Unauthorized 인증 실패 에러
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedException(SecurityException e) {
        ErrorDto errorResponse = ErrorStatus._UNAUTHORIZED.convertErrorDto();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // 403 Forbidden 권한 부족 에러
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleForbiddenException(AccessDeniedException e) {
        ErrorDto errorResponse = ErrorStatus._FORBIDDEN.convertErrorDto();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // 409 Conflict 서버와 현재 상태 충돌 에러
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDto> handleConflictException(IllegalStateException e) {
        ErrorDto errorResponse = ErrorStatus._CONFLICT.convertErrorDto();;
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    //500 INTERNAL_SERVER_ERROR 서버에러
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerException(NullPointerException e){
        ErrorDto errorResponse = ErrorStatus._INTERNAL_SERVER_ERROR.convertErrorDto();;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // 500 모든 예상하지 못한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleAllUnhandledExceptions(Exception e) {
        ErrorDto errorResponse = ErrorStatus._INTERNAL_SERVER_ERROR.convertErrorDto();;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
