package org.example.cornchat_be.domain.chat.repository;

import org.example.cornchat_be.domain.chat.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, Long> {
    // 채팅방 ID를 기준으로 메시지를 시간순으로 정렬하여 조회
    List<Message> findByChatRoomIdOrderBySendAtAsc(Long chatRoomId);

}