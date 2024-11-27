package org.example.cornchat_be.domain.chat.service;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.domain.chat.converter.MessageConverter;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.chat.repository.ChatRoomRepository;
import org.example.cornchat_be.domain.chat.repository.MessageRepository;
import org.example.cornchat_be.domain.chat.util.DateUtil;
import org.example.cornchat_be.domain.friend.entity.Friend;
import org.example.cornchat_be.domain.friend.repository.FriendRepository;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    //메시지 저장
    public ResponseDto.ResponseMessageDto saveMessage(RequestDto.RequestMessageDto requestMessageDto){
        //채팅방 유효성 검증
        if (!chatRoomRepository.existsById(requestMessageDto.getChatRoomId())){
            throw new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID);
        }

        //Message객체로 변환
        Message message = MessageConverter.createMessage(requestMessageDto);
        //Message저장
        Message savedMessage = messageRepository.save(message);

        //글쓴이 정보 가져오기
        User sender = userRepository.findByUserId(savedMessage.getSenderId())
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //글쓴이 닉네임 가져오기
        String senderName = sender.getUserName();
        return MessageConverter.convertResponseMessageDto(savedMessage, senderName);
    }
}