package org.example.cornchat_be.domain.friend.repository;

import org.example.cornchat_be.domain.friend.entity.Friend;
import org.example.cornchat_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    //user 친구들 조회
    List<Friend> findByUser(User user);

    //친구관계 중복 조회
    Optional<Friend> findByUserAndFriend(User user, User friend);

    //친구관계 유무
    Boolean existsByUserAndFriend(User user, User Friend);

    //친구 관계 삭제
    void deleteByUser(User user);
    void deleteByFriend(User friend);

}
