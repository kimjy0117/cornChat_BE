package org.example.cornchat_be.sms.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.sms.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/{email_addr}/authcode")
    public ResponseEntity<String> sendEmailPath(@PathVariable String email_addr) throws MessagingException{
        emailService.sendEmail(email_addr);
        return ResponseEntity.ok("이메일을 확인하세요.");
    }

    @PostMapping("/{email_addr}/authcode")
    public ResponseEntity<String> sendEmailAndCode(@PathVariable String email_addr, @RequestParam("code") String code){
        if (emailService.verifyEmailCode(email_addr, code)){
            return ResponseEntity.ok("이메일 인증 성공");
        }
        return ResponseEntity.notFound().build();
    }
}
