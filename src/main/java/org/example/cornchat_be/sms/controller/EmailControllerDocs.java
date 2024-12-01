package org.example.cornchat_be.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.example.cornchat_be.sms.dto.SmsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Email인증", description = "Email인증 관련 API입니다.")
public interface EmailControllerDocs {

    @Operation(summary = "회원가입 인증번호 발송", description = "회원가입 시 인증번호를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "인증번호 발송 성공"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일이 있습니다.")
    })
    public ResponseEntity<?> sendJoinEmailPath(@RequestBody SmsDto.EmailDto emailDto) throws MessagingException;

    @Operation(summary = "비밀번호 변경 인증번호 발송", description = "비밀번호 변경 시 인증번호를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "인증번호 발송 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 이메일 주소입니다.")
    })
    public ResponseEntity<?> sendFindPasswordEmailPath(@RequestBody SmsDto.EmailDto emailDto) throws MessagingException;

    @Operation(summary = "인증번호 검증", description = "인증번호가 맞는지 검사합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호가 일치합니다."),
            @ApiResponse(responseCode = "400", description = "인증번호가 일치하지 않습니다."),
            @ApiResponse(responseCode = "500", description = "서버측 인증번호가 존재하지 않습니다.")
    })
    public ResponseEntity<?> sendEmailAndCode(@RequestBody SmsDto.VerifyCodeDto verifyCodeDto);

}
