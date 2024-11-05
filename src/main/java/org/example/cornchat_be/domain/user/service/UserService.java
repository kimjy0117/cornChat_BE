package org.example.cornchat_be.domain.user.service;

import org.example.cornchat_be.domain.user.converter.UserConverter;
import org.example.cornchat_be.domain.user.dto.UserRequestDto;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User joinUser(UserRequestDto.JoinDto request){
        //비밀번호 검사
        User newUser = UserConverter.createUser(request, bCryptPasswordEncoder);
        return userRepository.save(newUser);
    }

}
