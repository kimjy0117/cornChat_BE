package org.example.cornchat_be.domain.chat.converter;

import org.example.cornchat_be.domain.chat.entity.ChatRoom;
import org.example.cornchat_be.domain.chat.entity.ChatRoomMember;
import org.example.cornchat_be.domain.user.entity.User;

public class ChatRoomMemberConverter {
    public static ChatRoomMember createChatRoomMember (ChatRoom chatRoom, User user){
        return ChatRoomMember.builder()
                .chatRoomTitle(chatRoom.getTitle())
                .chatRoom(chatRoom)
                .user(user)
                .build();
    }
}
