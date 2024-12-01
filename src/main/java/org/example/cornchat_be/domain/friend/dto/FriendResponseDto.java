package org.example.cornchat_be.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FriendResponseDto {
     private String friendName;
     private String friendNickname;
     private String friendStatusMessage;
     private String friendId;
     //프로필 이미지도 추후 추가해야됨
}
