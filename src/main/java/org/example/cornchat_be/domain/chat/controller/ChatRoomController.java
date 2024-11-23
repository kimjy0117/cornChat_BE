package org.example.cornchat_be.domain.chat.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.SuccessStatus;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.ChatRoom;
import org.example.cornchat_be.domain.chat.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController implements ChatRoomControllerDocs {
    private final ChatRoomService chatRoomService;

    //채팅방 생성
    @PostMapping
    public ResponseEntity<?> createChatRoom(@RequestBody RequestDto.ChatRoomRequestDto chatRoomRequestDto){
        ChatRoom response = chatRoomService.createChatRoom(chatRoomRequestDto);
        //추후에 responseDto로 변환해야됨
        return ResponseEntity.status(SuccessStatus._CREATE_CHATROOM_SUCCESS.getHttpStatus())
                .body(SuccessStatus._CREATE_CHATROOM_SUCCESS.convertSuccessDto(response));
    }

    //개인 채팅방 생성
    @PostMapping("/dm")
    public ResponseEntity<?> createDmChatRoom(@RequestParam String friendId){
        ChatRoom chatRoom = chatRoomService.createDm(friendId);
        //추후에 responseDto로 변환해야됨
        return ResponseEntity.ok(SuccessStatus._CREATE_CHATROOM_SUCCESS.convertSuccessDto(chatRoom));
    }

    //채팅방 리스트 가져오기
    @GetMapping
    public ResponseEntity<?> getUserChatRooms(){
        List<ResponseDto.ChatRoomResponseDto> response = chatRoomService.getUserChatRooms();
        return ResponseEntity.status(SuccessStatus._GET_CHATROOM_LIST_SUCCESS.getHttpStatus())
                .body(SuccessStatus._GET_CHATROOM_LIST_SUCCESS.convertSuccessDto(response));
    }

    //채팅방 정보 조회
    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getChatRoomDetails(@PathVariable Long roomId){
        //채팅방 정보 조회
        ChatRoom chatRoom = chatRoomService.getChatRoomDetails(roomId);
        //추후에 responseDto로 변환해야됨
        return ResponseEntity.ok(SuccessStatus._GET_CHATROOM_DETAILS_SUCCESS.convertSuccessDto(chatRoom));
    }

    //채팅방 초대
    @PostMapping("/{roomId}")
    public ResponseEntity<?> addMemberToChatRoom(@PathVariable Long roomId, @RequestBody String friendId){
        chatRoomService.addMemberToChatRoom(roomId, friendId);
        return ResponseEntity.ok(SuccessStatus._ADD_FRIEND_CHATROOM_SUCCESS.convertSuccessDto());
    }

    //채팅방 나가기
    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> leaveChatRoom(@PathVariable Long roomId){
        chatRoomService.leaveChatRoom(roomId);
        return ResponseEntity.status(SuccessStatus._LEAVE_CHATROOM_SUCCESS.getHttpStatus())
                .body(SuccessStatus._LEAVE_CHATROOM_SUCCESS.convertSuccessDto());
    }
}
