package org.example.cornchat_be.domain.chat.repository;


import org.example.cornchat_be.domain.chat.entity.ChatRoom;
import org.example.cornchat_be.domain.chat.entity.ChatRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT r FROM ChatRoom r JOIN r.members m WHERE m.id = :userId")
    List<ChatRoom> findChatRoomsByUserId(@Param("userId") Long userId);

    //사용자와 친구와의 dm채팅방이 있는지 조회
    @Query("""
    SELECT cr
    FROM ChatRoom cr
    JOIN cr.members m1
    JOIN cr.members m2
    WHERE cr.type = :type
      AND m1.user.id = :userId
      AND m2.user.userId = :friendId
""")
    Optional<ChatRoom> findDirectMessageRoom(
            @Param("userId") Long userId,
            @Param("friendId") String friendId,
            @Param("type") ChatRoomType type
    );


}
