package org.example.cornchat_be.domain.chat.dto;

import lombok.*;
import org.example.cornchat_be.domain.chat.entity.ChatRoomType;

import java.time.LocalDateTime;
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
        private ChatRoomType type; //DM or GROUP
        private List<String> memberIds;
    }
}
