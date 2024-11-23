package org.example.cornchat_be.domain.chat.repository;

import org.example.cornchat_be.domain.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findByChatRoomId(Long roomId);

    Optional<ChatRoomMember> findByChatRoomIdAndUserId(Long roomId, Long userId);

    void deleteByChatRoomIdAndUserId(Long roomId, Long userId);

    boolean existsByChatRoomIdAndUserUserId(Long roomId, String userId);
}
