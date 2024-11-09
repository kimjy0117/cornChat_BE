package org.example.cornchat_be.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "유저", description = "User 관련 API입니다.")
public interface UserControllerDocs {

    @Operation(summary = "이메일 중복검사", description = "동일한 이메일이 있는지 검사합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 사용 가능"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일이 있습니다.")
    })
    public ResponseEntity<?> checkEmail(@RequestBody UserRequestDto.EmailDto email);

    @Operation(summary = "아이디 중복검사", description = "동일한 아이디가 있는지 검사합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이디 사용 가능"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디가 있습니다.")
    })
    public ResponseEntity<?> checkUserId(@RequestBody UserRequestDto.UserIdDto userId);


    @Operation(summary = "회원가입", description = "새로운 사용자를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "중복된 정보가 있습니다.")
    })
    public ResponseEntity<?> join(@RequestBody UserRequestDto.JoinDto request);

    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "409", description = "기존 비밀번호와 동일합니다.")
    })
    public ResponseEntity<?> findPw(@RequestBody UserRequestDto.FindPwDto request);
}
