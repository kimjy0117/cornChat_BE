package org.example.cornchat_be.domain.chat.dto;

import lombok.*;

import java.util.List;

public class RequestDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestMessageDto{
        private String senderId;
        private Long chatRoomId;
        private String content;
        private String messageType;
    }

    @Getter
    public static class ChatRoomRequestDto {
        private String title;
        private List<String> memberIds;
    }

    @Getter
    public static class FriendIdDto{
        private String friendId;
    }
}
