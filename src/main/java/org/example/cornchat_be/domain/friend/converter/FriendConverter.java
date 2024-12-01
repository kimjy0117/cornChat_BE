package org.example.cornchat_be.domain.friend.converter;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.domain.friend.dto.FriendResponseDto;
import org.example.cornchat_be.domain.friend.entity.Friend;

public class FriendConverter {
    public static FriendResponseDto createFriendResponseDto (Friend friend){
        return FriendResponseDto.builder()
                .friendNickname(friend.getFriendNickname())
                .friendName(friend.getFriend().getUserName())
                .friendStatusMessage(friend.getFriend().getStatusMessage())
                .friendId(friend.getFriend().getUserId())
                .build();
    }
}
