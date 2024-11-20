package org.example.cornchat_be.domain.user.repository;

import org.example.cornchat_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //검증
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNum(String phoneNum);
    Boolean existsByUserId(String userId);

    //조회
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    Optional<User> findByPhoneNum(String phoneNum);

    //제거
    void deleteByEmail(String email);
}
