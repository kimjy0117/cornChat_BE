package org.example.cornchat_be.sms.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.SuccessStatus;
import org.example.cornchat_be.sms.dto.SmsDto;
import org.example.cornchat_be.sms.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController implements EmailControllerDocs{
    private final EmailService emailService;

    //회원가입 인증번호 발송
    @PostMapping("/authcode/join")
    public ResponseEntity<?> sendJoinEmailPath(@RequestBody SmsDto.EmailDto emailDto) throws MessagingException{
        emailService.sendJoinEmail(emailDto);
        return ResponseEntity.status(SuccessStatus._SEND_CODE_SUCCESS.getHttpStatus())
                .body(SuccessStatus._SEND_CODE_SUCCESS.convertSuccessDto());
    }

    //비밀번호 변경 인증번호 발송
    @PostMapping("/authcode/findPassword")
    public ResponseEntity<?> sendFindPasswordEmailPath(@RequestBody SmsDto.EmailDto emailDto) throws MessagingException{
        emailService.sendFindPasswordEmail(emailDto);
        return ResponseEntity.status(SuccessStatus._SEND_CODE_SUCCESS.getHttpStatus())
                .body(SuccessStatus._SEND_CODE_SUCCESS.convertSuccessDto());
    }

    //인증번호 검증
    @PostMapping("/authcode")
    public ResponseEntity<?> sendEmailAndCode(@RequestBody SmsDto.VerifyCodeDto verifyCodeDto){
        emailService.verifyEmailCode(verifyCodeDto);
        return ResponseEntity.ok(SuccessStatus._VERIFY_CODE_SUCCESS.convertSuccessDto());
    }
}
