package org.example.cornchat_be.domain.chat.converter;

import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.ChatRoom;
import org.example.cornchat_be.domain.chat.entity.ChatRoomMember;
import org.example.cornchat_be.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomConverter {

    public static ChatRoom createChatRoom(RequestDto.ChatRoomRequestDto chatRoomRequestDto, User creator, List<ChatRoomMember> members){
        return ChatRoom.builder()
                .title(chatRoomRequestDto.getTitle())
                .type(chatRoomRequestDto.getType())
                .creator(creator)
                .members(members)
                .build();
    }

    public static ResponseDto.ChatRoomResponseDto convertToChatRoomDto(ChatRoom chatRoom){
        return ResponseDto.ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .type(chatRoom.getType())
                .members(chatRoom.getMembers().stream()
                        .map(member -> member.getUser().getUserName()).collect(Collectors.toList()))
                .build();
    }
}
