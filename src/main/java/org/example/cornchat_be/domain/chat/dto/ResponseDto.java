package org.example.cornchat_be.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cornchat_be.domain.chat.entity.ChatRoomType;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseMessageDto{
        private Long id;
        private String senderId;
        private Long chatRoomId;
        private String content;
        private String messageType;
        private LocalDateTime sendAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChatRoomResponseDto{
        private Long id;
        private String title;
        private ChatRoomType type; //DM or GROUP
        private List<String> members;
        private LocalDateTime createAt;
    }

    @AllArgsConstructor
    public static class CreateChatRoomDto{
        private Long id;
        private String title;
        private int memberCount;
    }
}
