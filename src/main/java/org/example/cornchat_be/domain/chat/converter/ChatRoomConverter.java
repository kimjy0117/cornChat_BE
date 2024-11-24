package org.example.cornchat_be.domain.chat.converter;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.ChatRoom;
import org.example.cornchat_be.domain.chat.entity.ChatRoomMember;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChatRoomConverter {

    public static ChatRoom createChatRoom(RequestDto.ChatRoomRequestDto chatRoomRequestDto, User creator, List<ChatRoomMember> members){
        return ChatRoom.builder()
                .title(chatRoomRequestDto.getTitle())
                .members(members)
                .build();
    }

    public static ResponseDto.ChatRoomResponseDto convertToChatRoomDto(ChatRoom chatRoom){
        return ResponseDto.ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .type(chatRoom.getType())
                .members(chatRoom.getMembers().stream()
                        .map(member -> member.getUser().getUserName())
                        .collect(Collectors.toList()))
                .createAt(chatRoom.getCreateAt())
                .build();
    }

    public static ResponseDto.ChatRoomListResponseDto convertToChatRoomListDto(ChatRoom chatRoom, Message message){
        LocalDateTime latestAt = null;

        //만약 마지막 메시지가 없으면 채팅방이 생성된 시간을 넣음
        if(message.getSenderId().isEmpty()){
            latestAt = chatRoom.getCreateAt();
        } else{
            latestAt = message.getSendAt();
        }

        return ResponseDto.ChatRoomListResponseDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .members(chatRoom.getMembers().stream()
                        .map(member -> member.getUser().getUserName())
                        .collect(Collectors.toList()))
                .lastMessage(message.getContent())
                .latestMessageAt(latestAt)
                .build();
    }
}
