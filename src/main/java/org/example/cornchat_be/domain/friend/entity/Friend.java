package org.example.cornchat_be.domain.friend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cornchat_be.domain.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String friendNickname;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @CreationTimestamp
    private LocalDateTime joinedAt;
}