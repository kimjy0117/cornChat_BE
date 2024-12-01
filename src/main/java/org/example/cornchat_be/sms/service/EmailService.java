package org.example.cornchat_be.sms.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.sms.dto.SmsDto;
import org.example.cornchat_be.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String configEmail;

    //인증번호 생성
    private String createCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); //6자리 숫자
        return String.valueOf(code);
    }

    //Thymeleaf기반의 html과 연결
    private String setContext(String code){
        Context context = new Context();
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();


        context.setVariable("code", code);

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("mail", context);
    }

    // 메일 양식 만드는 메소드
    private MimeMessage createEmailForm(String email) throws MessagingException {

        String authCode = createCode();

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("안녕하세요 인증번호입니다.");
        message.setFrom(configEmail);
        message.setText(setContext(authCode), "utf-8", "html");

        redisUtil.setData(email, authCode, 60 * 30L);

        return message;
    }

    // 회원가입 인증메일 보내는 메소드
    public void sendJoinEmail(SmsDto.EmailDto emailDto) throws MessagingException{
        String email = emailDto.getEmail();
        //가입된 이메일이 있으면 에러처리
        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_EMAIL);
        }

        if(redisUtil.existData(email)){
            redisUtil.deleteData(email);
        }

        MimeMessage emailForm = createEmailForm(email);

        mailSender.send(emailForm);
    }

    // 비밀번호 변경 인증메일 보내는 메소드
    public void sendFindPasswordEmail(SmsDto.EmailDto emailDto) throws MessagingException{
        String email = emailDto.getEmail();
        //가입된 이메일이 없으면 에러처리
        if(!userRepository.existsByEmail(email)){
            throw new CustomException(ErrorStatus._ALREADY_NOT_EXIST_EMAIL);
        }

        if(redisUtil.existData(email)){
            redisUtil.deleteData(email);
        }

        MimeMessage emailForm = createEmailForm(email);

        mailSender.send(emailForm);
    }

    // 코드 검증 메소드
    public void verifyEmailCode(SmsDto.VerifyCodeDto verifyCodeDto){
        String email = verifyCodeDto.getEmail();
        String code = verifyCodeDto.getCode();

        String codeFoundByEmail = redisUtil.getData(email);
        if (codeFoundByEmail == null) {
            throw new NullPointerException("서버측 인증번호가 존재하지 않습니다.");
        }

        if (!codeFoundByEmail.equals(code)){
            throw new CustomException(ErrorStatus._VERIFY_CODE_FAIL);
        }
    }
}
