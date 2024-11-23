package org.example.cornchat_be.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.domain.chat.converter.MessageConverter;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    // 채팅방의 메시지 기록을 시간순으로 조회
    public List<ResponseDto.ResponseMessageDto> getChatHistory(Long chatRoomId) {
        // chatRoomId에 해당하는 메시지들만 조회하고, 시간순으로 정렬
        List<Message> messages = messageRepository.findByChatRoomIdOrderBySendAtAsc(chatRoomId);

        // ChatMessage를 MessageDto로 변환
        return messages.stream()
                .map(MessageConverter::convertResponseMessageDto)
                .collect(Collectors.toList());
    }
}
