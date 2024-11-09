package org.example.cornchat_be.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.domain.user.converter.UserConverter;
import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //이메일 중복검사
    public void checkEmail(UserRequestDto.EmailDto email){
        if(userRepository.existsByEmail(email.getEmail())){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_EMAIL);
        }
    }

    //아이디 중복검사
    public void checkUserId(UserRequestDto.UserIdDto userId){
        if (userRepository.existsByUserId(userId.getUserId())){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_USERID);
        }
    }

    //회원가입
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
    public void findPw(UserRequestDto.FindPwDto request){
        //기존 유저정보 가져옴
        User updateUser = userRepository.findByEmail(request.getEmail());
        //새 비번을 암호화
        String newPassword = bCryptPasswordEncoder.encode(request.getPassword());

        //기존 비번과 새 비번이 같을 경우 에러처리
        if(updateUser.getPassword().equals(newPassword)){
            throw new CustomException(ErrorStatus._ALREADY_EXIST_PASSWORD);
        }

        //비밀번호 변경 로직
        updateUser.setPassword(newPassword);
        userRepository.save(updateUser);
    }
}
