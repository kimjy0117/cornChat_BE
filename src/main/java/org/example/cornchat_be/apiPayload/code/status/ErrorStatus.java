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

    // 사용자 정보
    _ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "EXISTEMAIL409", "이미 사용중인 이메일입니다."),
    _ALREADY_NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "NOTEXISTEMAIL400", "존재하지 않는 이메일 주소입니다."),
    _ALREADY_EXIST_USERID(HttpStatus.CONFLICT, "USERID409", "이미 사용중인 아이디입니다."),
    _ALREADY_EXIST_NUMBER(HttpStatus.CONFLICT, "PHONE409", "이미 사용중인 번호입니다."),
    _ALREADY_EXIST_PASSWORD(HttpStatus.CONFLICT, "PASSWORD409", "기존 비밀번호와 일치합니다. 새로운 비밀번호를 입력해주세요."),
    _NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "NOTEXISTUSER400", "존재하지 않는 사용자입니다."),

    // 친구
    _DUPLICATED_FRIEND(HttpStatus.CONFLICT, "DUPFRIEND409", "이미 등록된 친구입니다."),
    _ADD_FRIEND_MYSELF(HttpStatus.BAD_REQUEST, "ADDMYSELF404", "자신은 친구로 추가할 수 없습니다."),

    //이메일 인증
    _VERIFY_CODE_FAIL(HttpStatus.BAD_REQUEST, "VERIFYCODE400", "인증번호가 일치하지 않습니다."),

    //채팅
    _NOT_EXIST_ROOM_ID(HttpStatus.BAD_REQUEST, "NOTEXISTROOMID400", "존재하지 않는 채팅방입니다."),
    _ALREADY_EXIST_FRIEND(HttpStatus.CONFLICT, "EXISTFRINED409", "이미 초대된 친구입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ErrorDto convertErrorDto(){
        return ErrorDto.builder()
                .status(httpStatus)
                .errorCode(code)
                .message(message)
                .build();
    }
}
