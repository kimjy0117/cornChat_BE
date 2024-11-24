package org.example.cornchat_be.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "채팅방", description = "ChatRoom 관련 API입니다.")
public interface ChatRoomControllerDocs {
    @Operation(summary = "채팅방 생성", description = "그룹채팅방을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "채팅방 생성 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 친구 정보입니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> createChatRoom(@RequestBody RequestDto.ChatRoomRequestDto chatRoomRequestDto);

    //개인 채팅방 생성
    @Operation(summary = "개인 채팅방 생성", description = "개인 채팅방을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅방 생성 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 친구 정보입니다."),
            @ApiResponse(responseCode = "400", description = "친구 관계가 존재하지 않습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> createDmChatRoom(@RequestBody RequestDto.FriendIdDto friendIdDto);

    //채팅방 리스트 가져오기
    @Operation(summary = "채팅방 리스트 가져오기", description = "채팅방 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅방 리스트 조회성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> getUserChatRooms();

    //채팅방 정보 조회
    @Operation(summary = "채팅방 정보 가져오기", description = "채팅방 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅방 정보 조회성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 채팅방입니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> getChatRoomDetails(@PathVariable Long roomId);

    //채팅방 초대
    @Operation(summary = "채팅방 초대하기", description = "친구를 채팅방에 초대합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구 초대 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 채팅방입니다."),
            @ApiResponse(responseCode = "400", description = "개인 채팅방은 친구추가를 할 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> addMemberToChatRoom(@PathVariable Long roomId, @RequestBody RequestDto.FriendIdDto friendIdDto);

    //채팅방 나가기
    @Operation(summary = "채팅방 나가기", description = "채팅방을 나갑니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "채팅방 나가기 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 채팅방입니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> leaveChatRoom(@PathVariable Long roomId);
}
