package org.example.cornchat_be.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.cornchat_be.domain.common.BaseEntity;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


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
    private String role;

    @CreatedDate
    private LocalDateTime createdAt;
}
