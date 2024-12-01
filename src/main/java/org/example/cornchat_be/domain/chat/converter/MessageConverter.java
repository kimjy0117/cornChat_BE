package org.example.cornchat_be.domain.chat.converter;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.chat.util.DateUtil;
import org.example.cornchat_be.domain.friend.entity.Friend;
import org.example.cornchat_be.domain.friend.repository.FriendRepository;
import org.example.cornchat_be.domain.user.entity.User;

import java.time.LocalDateTime;

public class MessageConverter {
    public static Message createMessage (RequestDto.RequestMessageDto messageDto){
        return Message.builder()
                .chatRoomId(messageDto.getChatRoomId())
                .senderId(messageDto.getSenderId())
                .content(messageDto.getContent())
                .messageType(messageDto.getMessageType())
                .sendAt(DateUtil.convertToDate(LocalDateTime.now()))
                .build();
    }

    public static ResponseDto.ResponseMessageDto convertResponseMessageDto (Message message, String senderName){
        return ResponseDto.ResponseMessageDto.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoomId())
                .senderId(message.getSenderId())
                .senderName(senderName)
                .content(message.getContent())
                .messageType(message.getMessageType())
                .sendAt(DateUtil.convertToLocalDateTime(message.getSendAt()))
                .build();
    }
}
