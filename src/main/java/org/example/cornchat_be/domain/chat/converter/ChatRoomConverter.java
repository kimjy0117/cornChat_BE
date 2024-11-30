package org.example.cornchat_be.domain.chat.converter;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.ChatRoom;
import org.example.cornchat_be.domain.chat.entity.ChatRoomMember;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.chat.util.DateUtil;
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

    public static ResponseDto.ChatRoomListResponseDto convertToChatRoomListDto(ChatRoom chatRoom, String chatRoomTitle, Message message){
        LocalDateTime latestAt = LocalDateTime.MIN;

        //사용자가 지정한 채팅방 이름이 없으면, 기존 채팅방 이름 저장
        String title = chatRoom.getTitle();
        if( chatRoomTitle != null && !chatRoomTitle.isBlank()){
            title = chatRoomTitle;
        }

        //만약 마지막 메시지가 없으면 채팅방이 생성된 시간을 넣음
        if(message.getSenderId().isBlank()){
            latestAt = chatRoom.getCreateAt();
        } else if (message.getSendAt() != null){
            // message.getSendAt()이 String 타입이므로, 이를 LocalDateTime으로 변환
            latestAt = DateUtil.convertToLocalDateTime(message.getSendAt());
        }

        return ResponseDto.ChatRoomListResponseDto.builder()
                .id(chatRoom.getId())
                .title(title)
                .members(chatRoom.getMembers().stream()
                    .map(member -> convertToChatRoomMemberInfoDto(member.getUser()))
                    .collect(Collectors.toList()))
                .lastMessage(message.getContent())
                .latestMessageAt(latestAt)
                .build();
    }

    public static ResponseDto.ChatRoomMemberInfoDto convertToChatRoomMemberInfoDto(User user){
        return ResponseDto.ChatRoomMemberInfoDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();
    }

    public static ResponseDto.ChatRoomDetailDto convertToChatRoomDetailDto (ChatRoom chatRoom, List<ResponseDto.ChatRoomMemberInfoDto> members){
        return ResponseDto.ChatRoomDetailDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .type(chatRoom.getType())
                .members(members)
                .memberCount(members.size())
                .build();
    }
}
