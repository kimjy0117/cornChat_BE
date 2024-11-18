package org.example.cornchat_be.domain.friend.controller;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.SuccessStatus;
import org.example.cornchat_be.domain.friend.dto.FriendResponseDto;
import org.example.cornchat_be.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/friends")
public class FriendController implements FriendControllerDocs {
    private final FriendService friendService;

    //친구 아이디로 친구요청
    @PostMapping("/requestById")
    public ResponseEntity<?> sendFriendRequestById(@RequestParam String friendId){
        friendService.addFriendByFriendId(friendId);
        return ResponseEntity.ok(SuccessStatus._ADD_FRIEND_SUCCESS.convertSuccessDto());
    }

    //친구 번호로 친구요청
    @PostMapping("/requestByPhoneNum")
    public ResponseEntity<?> sendFriendRequestByPhoneNum(@RequestParam String phoneNum){
        friendService.addFriendByFriendPhoneNum(phoneNum);
        return ResponseEntity.ok(SuccessStatus._ADD_FRIEND_SUCCESS.convertSuccessDto());
    }
    
    //친구 목록 조회
    @GetMapping
    public ResponseEntity<?> getFriends(){
        List<FriendResponseDto> friends = friendService.getFriends();
        return ResponseEntity.ok(SuccessStatus._FIND_FRIEND_LIST_SUCCESS.convertSuccessDto(friends));
    }
    
    //친구 아이디로 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFriendByFriendId(@RequestParam String friendId){
        friendService.removeFriend(friendId);
        return ResponseEntity.status(SuccessStatus._DELETE_FRIEND_SUCCESS.getHttpStatus())
                .body(SuccessStatus._DELETE_FRIEND_SUCCESS.convertSuccessDto());
    }

}
