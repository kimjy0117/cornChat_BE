package org.example.cornchat_be.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cornchat_be.domain.chat.role.ChatRoomType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private ChatRoomType type; // DM or GROUP

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatRoomMember> members = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createAt;

    // 멤버 추가 로직
    public void addMember(ChatRoomMember member) {
        members.add(member);
        member.setChatRoom(this);
    }

    // 멤버 제거 로직
    public void removeMember(ChatRoomMember member) {
        members.remove(member);
        member.setChatRoom(null);
    }
}
