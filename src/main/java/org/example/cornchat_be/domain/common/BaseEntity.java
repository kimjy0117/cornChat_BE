package org.example.cornchat_be.domain.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass //JPA엔티티가 아님을 명시, 다른 엔티티들이 이 클래스를 상속 받도록 함
@EntityListeners(AuditingEntityListener.class) //jpa auditing기능을 사용하기 위한 리스너로 엔티티가 생성 또는 수정될 때 자동으로 타임 스탬프를 기록할 수 있다.
public abstract class BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}
