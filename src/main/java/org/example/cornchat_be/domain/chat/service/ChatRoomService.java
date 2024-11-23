package org.example.cornchat_be.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.domain.chat.converter.ChatRoomConverter;
import org.example.cornchat_be.domain.chat.converter.ChatRoomMemberConverter;
import org.example.cornchat_be.domain.chat.dto.RequestDto;
import org.example.cornchat_be.domain.chat.dto.ResponseDto;
import org.example.cornchat_be.domain.chat.entity.ChatRoom;
import org.example.cornchat_be.domain.chat.entity.ChatRoomMember;
import org.example.cornchat_be.domain.chat.entity.ChatRoomType;
import org.example.cornchat_be.domain.chat.repository.ChatRoomMemberRepository;
import org.example.cornchat_be.domain.chat.repository.ChatRoomRepository;
import org.example.cornchat_be.domain.friend.entity.Friend;
import org.example.cornchat_be.domain.friend.repository.FriendRepository;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    //채팅방 만들기
    public ChatRoom createChatRoom(RequestDto.ChatRoomRequestDto chatRoomRequestDto) {
        //현재 접속한 유저 정보 가져옴
        User currentUser = securityUtil.getCurrentUser();

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(chatRoom.getTitle());
        chatRoom.setType(ChatRoomType.GROUP);
        chatRoom.setCreator(currentUser);

        //chatRoomMember객체 리스트 생성
        List<ChatRoomMember> members = new ArrayList<>();
        for (String userId : chatRoomRequestDto.getMemberIds()) {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

            ChatRoomMember member = new ChatRoomMember();
            member.setChatRoom(chatRoom);
            member.setUser(user);

            //리스트에 멤버 추가
            members.add(member);
        }

        //멤버 리스트 저장
        chatRoomMemberRepository.saveAll(members);

        //채팅방 저장
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    //개인 채팅방 만들기
    public ChatRoom createDm(String friendId) {
        //현재 접속한 유저 정보 가져옴
        User user = securityUtil.getCurrentUser();

        //기존 DM방이 있는지 확인
        Optional<ChatRoom> existingDm = chatRoomRepository.findDirectMessageRoom(user.getId(), friendId, ChatRoomType.DM);

        //기존 DM방이 있으면 반환
        if (existingDm.isPresent()) {
            return existingDm.get();
        }

        //친구 정보 조회
        User friend = userRepository.findByUserId(friendId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //친구관계 정보 가져오기
        Friend friendRelation = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));


        //새로운 DM채팅방 생성
        ChatRoom newDm = new ChatRoom();
        newDm.setTitle(friendRelation.getFriendNickname());
        newDm.setCreator(user);
        newDm.setType(ChatRoomType.DM);

        //chatRoomMember객체 리스트 생성
        List<ChatRoomMember> members = new ArrayList<>();
        members.add(ChatRoomMemberConverter.createChatRoomMember(newDm, user));
        members.add(ChatRoomMemberConverter.createChatRoomMember(newDm, friend));
        chatRoomMemberRepository.saveAll(members);

        //DM채팅방에 멤버 객체 넣기
        newDm.setMembers(members);

        return chatRoomRepository.save(newDm);
    }

    //현재 접속한 유저의 채티방 리스트 가져오기
    public List<ResponseDto.ChatRoomResponseDto> getUserChatRooms() {
        //현재 접속한 유저 정보 가져옴
        User user = securityUtil.getCurrentUser();

        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByUserId(user.getId());
        return chatRooms.stream()
                .map(ChatRoomConverter::convertToChatRoomDto)
                .collect(Collectors.toList());
    }

    //채팅방 정보 조회
    public ChatRoom getChatRoomDetails(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID));
    }

    //채팅방 초대
    public void addMemberToChatRoom(Long roomId, String friendId) {
        //채팅방 존재 확인
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID));

        //개인 채팅방인지 확인
        if (chatRoom.getType().equals(ChatRoomType.DM)) {
            throw new IllegalArgumentException("개인 채팅방은 친구추가를 할 수 없습니다.");
        }

        //멤버인지 확인
        if (chatRoomMemberRepository.existsByChatRoomIdAndUserUserId(roomId, friendId)) {
            throw new CustomException(ErrorStatus._ALREADY_EXIST_FRIEND);
        }

        User user = userRepository.findByUserId(friendId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //새로운 멤버 추가
        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.setChatRoom(chatRoom);
        chatRoomMember.setUser(user);

        // 저장
        chatRoomMemberRepository.save(chatRoomMember);
    }

    //채팅방 나가기
    public void leaveChatRoom(Long roomId) {
        //현재 접속한 유저 정보 가져옴
        User user = securityUtil.getCurrentUser();

        //채팅방 정보 가져옴
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID));

        //채팅방 멤버에서 삭제
        chatRoomMemberRepository.deleteByChatRoomIdAndUserId(roomId, user.getId());

        //채팅방에 멤버가 없으면 채팅방 삭제
        if (chatRoomMemberRepository.findByChatRoomId(roomId).isEmpty()) {
            chatRoomRepository.delete(chatRoom);
        }
    }
}


