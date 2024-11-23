package org.example.cornchat_be.domain.chat.controller;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.SuccessStatus;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.chat.service.ChatService;
import org.example.cornchat_be.domain.chat.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;

    @MessageMapping("/chat/{roomId}")  // 클라이언트가 보내는 메시지
    @SendTo("/sub/chat/{roomId}")  // 클라이언트가 받는 메시지
    public ResponseDto.ResponseMessageDto sendMessage(@DestinationVariable Long roomId,
                                                      @Payload RequestDto.RequestMessageDto requestMessageDto) {
        requestMessageDto.setChatRoomId(roomId);

        //메시지를 저장 후 dto반환
        return chatService.saveMessage(requestMessageDto);
    }


    //특정 채팅방의 메시지 가져오기
    @GetMapping("/api/messages/{roomId}")
    public ResponseEntity<?> getMessagesByRoom(@PathVariable Long roomId){
        //채팅방의 메시지 리스트를 조회
        List<ResponseDto.ResponseMessageDto> chatHistory = messageService.getChatHistory(roomId);

        //메시지가 없으면 빈 리스트로 응답
        if (chatHistory.isEmpty()){
            return ResponseEntity.status(SuccessStatus._GET_CHAT_HISTORY_EMPTY_SUCCESS.getHttpStatus())
                    .body(SuccessStatus._GET_CHAT_HISTORY_EMPTY_SUCCESS.convertSuccessDto());
        }
        return ResponseEntity.status(SuccessStatus._GET_CHAT_HISTORY_SUCCESS.getHttpStatus())
                .body(SuccessStatus._GET_CHAT_HISTORY_SUCCESS.convertSuccessDto(chatHistory));
    }
}
