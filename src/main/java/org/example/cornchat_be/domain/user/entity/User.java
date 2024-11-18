package org.example.cornchat_be.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cornchat_be.domain.friend.entity.Friend;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String email;
    private String phoneNum;
    private String userId;
    private String password;
//    private Image profileImage;
    private String statusMessage;
    private String role;

    // 친구 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friends = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
}
