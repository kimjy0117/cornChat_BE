package org.example.cornchat_be.domain.chat.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "messages")
@Builder
@Getter
public class Message {
    //MongoDb는 기본적으로 String형식의 ObjectId를 사용해서 Id를 생성한다. 그렇기 때문에 id를 String형으로 해준다.
    @Id
    private String id;
    private Long chatRoomId;
    private String senderId;
    private String content;
    private String messageType; // TEXT, IMAGE

    //몽고db는 LocalDateTime형식을 지원하지 않는다.
    private Date sendAt;
}