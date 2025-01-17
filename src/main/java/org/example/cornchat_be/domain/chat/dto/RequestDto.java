package org.example.cornchat_be.domain.chat.dto;

import lombok.*;
import org.example.cornchat_be.domain.chat.role.MessageType;

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
        private MessageType messageType;
    }

    @Getter
    public static class ChatRoomRequestDto {
        private String title;
        private List<String> memberIds;
    }

    @Getter
    public  static class ChatRoomTitleDto{
        private String title;
    }

    @Getter
    public static class FriendIdDto{
        private String friendId;
    }

    @Getter
    public static class FriendIdsDto{
        private List<String> memberIds;
    }


}
