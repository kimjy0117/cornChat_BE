package org.example.cornchat_be.domain.friend.dto;

import lombok.Getter;

public class FriendRequestDto {
    @Getter
    public static class friendNameDto{
        private String friendId;
        private String friendName;
    }
}
