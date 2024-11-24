package org.example.cornchat_be.domain.chat.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "messages")
@Builder
@Getter
public class Message {
    @Id
    private Long id;
    private Long chatRoomId;
    private String senderId;
    private String content;
    private String messageType; // TEXT, IMAGE
//    private List<String> readBy = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime sendAt;
}