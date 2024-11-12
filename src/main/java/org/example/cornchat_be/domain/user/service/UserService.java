package org.example.cornchat_be.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.domain.user.converter.UserConverter;
import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.jwt.dto.CustomUserDetails;
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

    //회원 탈퇴
    @Transactional
    public void deleteUser() {
        //현재 인증 객체(사용자 정보)를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null){
            throw new SecurityException("사용자 정보를 불러올 수 없습니다.");
        }
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            // CustomUserDetails에서 사용자 정보 추출
            String email = userDetails.getUsername(); // 사용자 이메일 가져오기

            System.out.println("User email: " + email);

            //이메일에 해당하는 사용자 삭제
            userRepository.deleteByEmail(email);
        } else {
            throw new SecurityException("잘못된 사용자 정보입니다.");
        }
    }
}
