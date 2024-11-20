package org.example.cornchat_be.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "유저", description = "User 관련 API입니다.")
public interface UserControllerDocs {

    @Operation(summary = "전화번호 중복검사", description = "동일한 전화번호가 있는지 검사합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전화번호 사용 가능합니다."),
            @ApiResponse(responseCode = "200", description = "중복된 전화번호입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> checkPhoneNum(@RequestParam String phoneNum);

        @Operation(summary = "아이디 중복검사", description = "동일한 아이디가 있는지 검사합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이디 사용 가능합니다."),
            @ApiResponse(responseCode = "200", description = "중복된 아이디입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> checkUserId(@RequestParam String userId);


    @Operation(summary = "회원가입", description = "새로운 사용자를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "중복된 정보가 있습니다.")
    })
    public ResponseEntity<?> join(@RequestBody UserRequestDto.JoinDto joinDto);

    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "인증번호가 일치하지 않습니다."),
            @ApiResponse(responseCode = "409", description = "기존 비밀번호와 동일합니다."),
            @ApiResponse(responseCode = "500", description = "이메일 인증을 다시 시도해주세요")
    })
    public ResponseEntity<?> findPw(@RequestBody UserRequestDto.FindPwDto findPwDto);


    @Operation(summary = "회원 탈퇴", description = "회원 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "회원정보 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 사용자 정보입니다.")
    })
    public ResponseEntity<?> deleteUser();


    @Operation(summary = "회원 정보 조회", description = "회원 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 사용자 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> getUserProfile();


    @Operation(summary = "사용자 이름 수정", description = "사용자 이름을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "사용자 이름 수정 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 사용자 정보입니다."),
            @ApiResponse(responseCode = "409", description = "기존과 같은 이름입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> setUserName(@RequestParam String userName);

    @Operation(summary = "상태메시지 수정", description = "상태메시지를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "상태메시지 수정 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 사용자 정보입니다."),
            @ApiResponse(responseCode = "409", description = "기존과 같은 내용입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> setStatusMessage(@RequestParam String statusMessage);
}
