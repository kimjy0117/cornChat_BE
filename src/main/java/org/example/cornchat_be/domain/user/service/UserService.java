package org.example.cornchat_be.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.domain.user.converter.UserConverter;
import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.example.cornchat_be.domain.user.dto.UserResponseDto;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.SecurityUtil;
import org.example.cornchat_be.util.jwt.dto.CustomUserDetails;
import org.example.cornchat_be.util.redis.RedisUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisUtil redisUtil;
    private final SecurityUtil securityUtil;


    //전화번호 중복검사
    public boolean checkPhoneNum(String phoneNum){
        return userRepository.existsByPhoneNum(phoneNum);
    }

    //아이디 중복검사
    public boolean checkUserId(String userId){
        return userRepository.existsByUserId(userId);
    }

    //회원가입
    @Transactional
    public void joinUser(UserRequestDto.JoinDto request){
        User newUser = UserConverter.createUser(request, bCryptPasswordEncoder);
        //이메일 존재여부
        if (userRepository.existsByEmail(newUser.getEmail())){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_EMAIL);
        }
        //폰번호 존재여부
        if (userRepository.existsByPhoneNum(newUser.getPhoneNum())){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_NUMBER);
        }
        //아이디 존재여부
        if (userRepository.existsByUserId(newUser.getUserId())){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_USERID);
        }

        userRepository.save(newUser);
    }

    //비밀번호 변경
    @Transactional
    public void findPw(UserRequestDto.FindPwDto request){

        //인증번호가 일치하는지 한번 더 확인
        String codeFoundByEmail = redisUtil.getData(request.getEmail());
        if (codeFoundByEmail == null) {
            throw new NullPointerException("이메일 인증을 다시 시도해주세요.");
        }

        if (!codeFoundByEmail.equals(request.getCode())){
            throw new CustomException(ErrorStatus._VERIFY_CODE_FAIL);
        }

        //기존 유저정보 가져옴
        User updateUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //기존 비번과 새 비번이 같을 경우 에러처리
        if(bCryptPasswordEncoder.matches(request.getPassword(), updateUser.getPassword())){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_PASSWORD);
        }

        // 새 비번을 암호화하고 비밀번호 변경
        String newPassword = bCryptPasswordEncoder.encode(request.getPassword());
        updateUser.setPassword(newPassword);
        userRepository.save(updateUser);
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser() {
        //현재 사용자 정보 가져오기
        User user = securityUtil.getCurrentUser();

        //이메일에 해당하는 사용자 삭제
        userRepository.deleteByEmail(user.getEmail());
    }

    //회원 정보 가져오기
    public UserResponseDto.UserInformDto getUserProfile(){
        //현재 사용자 정보 가져오기
        User user = securityUtil.getCurrentUser();

        return UserConverter.convertToUserInformDto(user);
    }

    //유저 이름 바꾸기
    @Transactional
    public void setUserName(String userName){
        //현재 사용자 정보 가져오기
        User user = securityUtil.getCurrentUser();

        if (user.getUserName().equals(userName)){
            throw new IllegalStateException("기존 이름과 동일합니다.");
        }

        user.setUserName(userName);
        userRepository.save(user);
    }

    //상태 메시지 바꾸기
    @Transactional
    public void setStatusMessage(String statusMessage){
        //현재 사용자 정보 가져오기
        User user = securityUtil.getCurrentUser();

        if (user.getStatusMessage().equals(statusMessage)){
            throw new IllegalStateException("기존 상태메시지와 동일합니다.");
        }

        user.setStatusMessage(statusMessage);
        userRepository.save(user);
    }

}
