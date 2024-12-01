package org.example.cornchat_be.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.domain.chat.converter.MessageConverter;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.chat.repository.MessageRepository;
import org.example.cornchat_be.domain.friend.entity.Friend;
import org.example.cornchat_be.domain.friend.repository.FriendRepository;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    // 채팅방의 메시지 기록을 시간순으로 조회
    public List<ResponseDto.ResponseMessageDto> getChatHistory(Long chatRoomId) {
        // chatRoomId에 해당하는 메시지들만 조회하고, 시간순으로 정렬
        List<Message> messages = messageRepository.findByChatRoomIdOrderBySendAtAsc(chatRoomId);

        // ChatMessage를 MessageDto로 변환
        return messages.stream()
                .map(message -> {
                    User sender = userRepository.findByUserId(message.getSenderId())
                            .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));
                    // 기본 닉네임은 sender의 userName
                    String senderName = sender.getUserName();
                    // senderName을 이용해 메시지를 DTO로 변환
                    return MessageConverter.convertResponseMessageDto(message, senderName);
                })
                .collect(Collectors.toList());
    }
}
