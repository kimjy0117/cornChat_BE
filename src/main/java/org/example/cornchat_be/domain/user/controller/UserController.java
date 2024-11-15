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

    //전화번호 중복검사
    @GetMapping("/checkPhoneNum")
    public ResponseEntity<?> checkPhoneNum(@RequestParam String phoneNum){

        if (userService.checkPhoneNum(phoneNum)){
            //전화번호가 존재하면 true
            return ResponseEntity.ok(SuccessStatus._CHECK_PHONENUM_DUP_TRUE.convertSuccessDto(true));

        }
        //전화번호가 존재하지 않으면 false
        return ResponseEntity.ok(SuccessStatus._CHECK_PHONENUM_DUP_FALSE.convertSuccessDto(false));
    }

    //아이디 중복검사
    @GetMapping("/checkUserId")
    public ResponseEntity<?> checkUserId(@RequestParam String userId){

        if (userService.checkUserId(userId)){
            //아이디가 존재하면 true
            return ResponseEntity.ok(SuccessStatus._CHECK_USERID_DUP_TRUE.convertSuccessDto(true));
        }
        //아이디가 존재하지 않으면 false
        return ResponseEntity.ok(SuccessStatus._CHECK_USERID_DUP_FALSE.convertSuccessDto(false));
    }

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequestDto.JoinDto joinDto){
        userService.joinUser(joinDto);
        return ResponseEntity
                .status(SuccessStatus._JOIN_SUCCESS.getHttpStatus())
                .body(SuccessStatus._JOIN_SUCCESS.convertSuccessDto());
    }

    //비밀번호 변경
    @PatchMapping("/findPw")
    public ResponseEntity<?> findPw(@RequestBody UserRequestDto.FindPwDto findPwDto){
        userService.findPw(findPwDto);
        return ResponseEntity.ok(SuccessStatus._FIND_PASSWORD_SUCCESS.convertSuccessDto());
    }

    //회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        userService.deleteUser();
        return ResponseEntity.status(SuccessStatus._DELETE_USER_SUCCESS.getHttpStatus())
                .body(SuccessStatus._DELETE_USER_SUCCESS.convertSuccessDto());
    }

}
