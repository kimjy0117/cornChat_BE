package org.example.cornchat_be.domain.friend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.cornchat_be.domain.friend.dto.FriendRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "친구", description = "Friend 관련 API입니다.")
public interface FriendControllerDocs {

    @Operation(summary = "아이디로 친구추가", description = "친구 아이디로 친구추가를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구추가 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 사용자입니다."),
            @ApiResponse(responseCode = "400", description = "접속한 사용자 정보를 읽을 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "사용자 정보를 불러올 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "409", description = "이미 등록된 친구입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> sendFriendRequestById(@RequestParam String friendId);

    @Operation(summary = "핸드폰 번호로 친구추가", description = "친구 핸드폰번호로 친구추가를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구추가 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 사용자입니다."),
            @ApiResponse(responseCode = "400", description = "접속한 사용자 정보를 읽을 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "사용자 정보를 불러올 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "409", description = "이미 등록된 친구입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> sendFriendRequestByPhoneNum(@RequestParam String phoneNum);

    @Operation(summary = "친구 목록 가져오기", description = "해당 유저의 친구목록을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구목록 가져오기 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 사용자입니다."),
            @ApiResponse(responseCode = "400", description = "친구 관계가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "접속한 사용자 정보를 읽을 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "사용자 정보를 불러올 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> getFriends();

    @Operation(summary = "친구 이름 수정", description = "친구 이름을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "친구이름 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 사용자입니다."),
            @ApiResponse(responseCode = "400", description = "친구 관계가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "접속한 사용자 정보를 읽을 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "사용자 정보를 불러올 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "409", description = "기존 이름과 동일합니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> setFriendName(@RequestBody FriendRequestDto.friendNameDto friendNameDto);

    @Operation(summary = "친구 삭제", description = "친구목록에서 해당 친구를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "친구삭제 성공"),
            @ApiResponse(responseCode = "400", description = "접속한 사용자 정보를 읽을 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "사용자 정보를 불러올 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> deleteFriendByFriendId(@RequestParam String friendId);

}
