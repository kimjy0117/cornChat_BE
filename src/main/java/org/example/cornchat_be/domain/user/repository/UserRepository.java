package org.example.cornchat_be.domain.user.repository;

import org.example.cornchat_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByPhoneNum(String phoneNum);
    Boolean existsByUserId(String userId);

    User findByEmail(String email);

    Void deleteByEmail(String email);
}
