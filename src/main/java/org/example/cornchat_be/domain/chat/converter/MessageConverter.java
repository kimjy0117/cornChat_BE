package org.example.cornchat_be.domain.chat.converter;

import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.Message;

public class MessageConverter {

    public static Message createMessage (RequestDto.RequestMessageDto messageDto){
        return Message.builder()
                .chatRoomId(messageDto.getChatRoomId())
                .senderId(messageDto.getSenderId())
                .content(messageDto.getContent())
                .messageType(messageDto.getMessageType())
                .build();
    }

    public static ResponseDto.ResponseMessageDto convertResponseMessageDto (Message message){
        return ResponseDto.ResponseMessageDto.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .messageType(message.getMessageType())
                .sendAt(message.getSendAt())
                .build();
    }
}
