package org.example.cornchat_be.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.SuccessStatus;
import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController implements UserControllerDocs {
    private final UserService userService;

    //이메일 중복검사
    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody UserRequestDto.EmailDto email){
        userService.checkEmail(email);
        return ResponseEntity.ok(SuccessStatus._POST_OK.convertSuccessDto());
    }

    //아이디 중복검사
    @PostMapping("/checkUserId")
    public ResponseEntity<?> checkUserId(@RequestBody UserRequestDto.UserIdDto userId){
        userService.checkUserId(userId);
        return ResponseEntity.ok(SuccessStatus._POST_OK.convertSuccessDto());
    }

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequestDto.JoinDto request){
        userService.joinUser(request);
        return ResponseEntity
                .status(SuccessStatus._JOIN_SUCCESS.getHttpStatus())
                .body(SuccessStatus._JOIN_SUCCESS.convertSuccessDto());
    }

    //비밀번호 변경
    @PutMapping("/findPw")
    public ResponseEntity<?> findPw(@RequestBody UserRequestDto.FindPwDto request){
        userService.findPw(request);
        return ResponseEntity.ok(SuccessStatus._FIND_PASSWORD_SUCCESS.convertSuccessDto());
    }
}
