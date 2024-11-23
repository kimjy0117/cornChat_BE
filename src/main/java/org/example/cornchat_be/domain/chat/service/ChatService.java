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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    //메시지 저장
    public ResponseDto.ResponseMessageDto saveMessage(RequestDto.RequestMessageDto requestMessageDto){
        //채팅방 유효성 검증
        if (!chatRoomRepository.existsById(requestMessageDto.getChatRoomId())){
            throw new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID);
        }

        Message message = MessageConverter.createMessage(requestMessageDto);

        Message savedMessage = messageRepository.save(message);
        return mapToResponseDto(savedMessage);
    }

    //채팅방 별 메시지 가져오기
    public List<ResponseDto.ResponseMessageDto> getMessagesByRoom(Long roomId) {
        List<Message> messages = messageRepository.findByChatRoomIdOrderBySendAtAsc(roomId);
        return messages.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    private ResponseDto.ResponseMessageDto mapToResponseDto(Message message) {
        return ResponseDto.ResponseMessageDto.builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .chatRoomId(message.getChatRoomId())
                .content(message.getContent())
                .messageType(message.getMessageType())
                .sendAt(message.getSendAt())
                .build();
    }
}