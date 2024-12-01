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
    _POST_OK(HttpStatus.OK, "COMMON200", "200 OK"),
    _CREATE_OK(HttpStatus.CREATED,"COMMON201","201 Created."),
    _DELETE_OK(HttpStatus.ACCEPTED,"COMMON202","삭제되었습니다."),
    _PATCH_OK(HttpStatus.NO_CONTENT, "COMMON204", "업데이트 완료되었습니다."),

    // 유저
    _CHECK_USERID_DUP_TRUE(HttpStatus.OK, "IDCHECKTRUE200", "중복되는 아이디입니다."),
    _CHECK_USERID_DUP_FALSE(HttpStatus.OK, "IDCHECKFALSE200", "사용할 수 있는 아이디입니다."),
    _CHECK_PHONENUM_DUP_TRUE(HttpStatus.OK, "PHONECHECKTRUE200", "중복되는 전화번호입니다."),
    _CHECK_PHONENUM_DUP_FALSE(HttpStatus.OK, "PHONECHECKFALSE200", "사용할 수 있는 전화번호입니다."),
    _JOIN_SUCCESS(HttpStatus.OK, "JOIN201", "회원가입이 성공하였습니다."),
    _FIND_PASSWORD_SUCCESS(HttpStatus.NO_CONTENT, "PASSWORD204", "비밀번호 변경되었습니다."),
    _SET_USERNAME_SUCCESS(HttpStatus.NO_CONTENT, "SETUSERNAME204", "이름이 변경되었습니다."),
    _SET_STATUSMESSAGE_SUCCESS(HttpStatus.NO_CONTENT, "SETSTATUSMESSAGE204", "상태메시지가 변경되었습니다."),
    _SEND_CODE_SUCCESS(HttpStatus.CREATED, "SENDCODE201", "인증번호가 전송되었습니다."),
    _VERIFY_CODE_SUCCESS(HttpStatus.OK, "VERIFYCODE200", "이메일 인증이 성공하였습니다."),
    _DELETE_USER_SUCCESS(HttpStatus.ACCEPTED, "DLETEUSER202", "회원 탈퇴 되었습니다."),

    //친구
    _ADD_FRIEND_SUCCESS(HttpStatus.OK, "ADDFRIEND200", "친구 등록에 성공하였습니다."),
    _FIND_FRIEND_LIST_SUCCESS(HttpStatus.OK, "FINDFRIEND200", "친구 목록 가져오기에 성공하였습니다."),
    _SET_FRIENDNAME_SUCCESS(HttpStatus.NO_CONTENT, "SETFRIENDNAME204", "친구 이름이 변경되었습니다."),
    _DELETE_FRIEND_SUCCESS(HttpStatus.ACCEPTED, "DELETEFRIEND202", "해당 친구가 삭제되었습니다."),

    //채팅방
    _SET_ROOMTITLE_SUCCESS(HttpStatus.NO_CONTENT, "SETROOMTITLE204", "채팅방 이름이 변경되었습니다."),
    _CREATE_CHATROOM_SUCCESS(HttpStatus.CREATED, "CREATECHATROOM204", "채팅방이 생성되었습니다."),
    _GET_CHATROOM_DETAILS_SUCCESS(HttpStatus.OK, "GETCHATROOMDETAILS200", "채팅방 정보를 가져왔습니다."),
    _GET_CHAT_HISTORY_EMPTY_SUCCESS(HttpStatus.OK, "GETCHATEMPTY200", "채팅방 기록이 비어있습니다."),
    _GET_CHAT_HISTORY_SUCCESS(HttpStatus.OK, "GETCHAT200", "채팅방 기록을 성공적으로 가져왔습니다."),
    _GET_CHATROOM_LIST_SUCCESS(HttpStatus.OK, "GETCHATROOMLIST200", "채팅방 리스트를 성공적으로 가져왔습니다."),
    _LEAVE_CHATROOM_SUCCESS(HttpStatus.ACCEPTED, "LEAVECHATROOM202", "채팅방 나가기에 성공하였습니다."),
    _ADD_FRIEND_CHATROOM_SUCCESS(HttpStatus.OK, "ADDFRINEDCHATROOM200", "채팅방 초대에 성공하였습니다."),

    //채팅
    _SAVE_CHAT_MESSAGE_SUCCESS(HttpStatus.OK, "SAVECHAT201", "채팅 메시지가 저장되었습니다."),
    _SEND_ALARM_MESSAGE_SUCCESS(HttpStatus.OK, "SENDALARMMESSAGE200", "알람 메시지가 전송되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public <T> SuccessDto<T> convertSuccessDto(T data) {
        return SuccessDto.<T>builder()
                .successCode(code)
                .message(message)
                .data(data)
                .httpStatus(httpStatus)
                .build();
    }

    public <T> SuccessDto<T> convertSuccessDto() {
        return SuccessDto.<T>builder()
                .successCode(code)
                .message(message)
                .data(null)
                .httpStatus(httpStatus)
                .build();
    }
}
