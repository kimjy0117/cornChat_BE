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
import org.example.cornchat_be.domain.chat.role.ChatRoomType;
import org.example.cornchat_be.domain.chat.entity.Message;
import org.example.cornchat_be.domain.chat.repository.ChatRoomMemberRepository;
import org.example.cornchat_be.domain.chat.repository.ChatRoomRepository;
import org.example.cornchat_be.domain.chat.repository.MessageRepository;
import org.example.cornchat_be.domain.chat.role.FriendType;
import org.example.cornchat_be.domain.friend.entity.Friend;
import org.example.cornchat_be.domain.friend.repository.FriendRepository;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MessageRepository messageRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    //채팅방 만들기
    @Transactional
    public ResponseDto.ChatRoomResponseDto createChatRoom(RequestDto.ChatRoomRequestDto chatRoomRequestDto) {
        //현재 접속한 유저 정보 가져옴
        User currentUser = securityUtil.getCurrentUser();

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(chatRoomRequestDto.getTitle());
        chatRoom.setType(ChatRoomType.GROUP);

        //chatRoomMember객체 리스트 생성
        List<ChatRoomMember> members = new ArrayList<>();
        for (String userId : chatRoomRequestDto.getMemberIds()) {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

            //채팅방 멤버 생성
            ChatRoomMember member = ChatRoomMemberConverter.createChatRoomMember(chatRoom, user);

            //리스트에 멤버 추가
            members.add(member);
        }

        //현재 접속한 유저정보도 넣기
        members.add(ChatRoomMemberConverter.createChatRoomMember(chatRoom, currentUser));

        //멤버 리스트 저장
        chatRoomMemberRepository.saveAll(members);

        //채팅방 저장
        chatRoomRepository.save(chatRoom);

        return ChatRoomConverter.convertToChatRoomDto(chatRoom);
    }

    //개인 채팅방 만들기
    @Transactional
    public ResponseDto.ChatRoomResponseDto createDm(RequestDto.FriendIdDto friendIdDto) {
        //친구 아이디 가져오기
        String friendId = friendIdDto.getFriendId();

        //현재 접속한 유저 정보 가져옴
        User user = securityUtil.getCurrentUser();

        //친구 정보 조회
        User friend = userRepository.findByUserId(friendId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //채팅방 조회를 위해 list로 만들어줌
        List<User> users = Arrays.asList(user, friend);

        //기존 DM방이 있는지 확인
        Optional<ChatRoom> existingDm = chatRoomRepository.findDmChatRoomsWithUsers(users);

        //기존 DM방이 있으면 반환
        if (existingDm.isPresent()) {
            System.out.println("기존 dm방 반환");
            return  ChatRoomConverter.convertToChatRoomDto(existingDm.get());
        }

        //채팅방 제목
        String roomTitle = "나와의 채팅방";

        //자신이 아니라면
        if (!user.getUserId().equals(friend.getUserId())) {
            //친구관계 정보 가져오기
            Friend friendRelation = friendRepository.findByUserAndFriend(user, friend)
                    .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));
            roomTitle = friendRelation.getFriendNickname() + ", " + user.getUserName() + "님 채팅방";
        }

        //새로운 DM채팅방 생성
        ChatRoom newDm = new ChatRoom();
        newDm.setTitle(roomTitle);
        newDm.setType(ChatRoomType.DM);

        //chatRoomMember객체 리스트 생성
        List<ChatRoomMember> members = new ArrayList<>();
        members.add(ChatRoomMemberConverter.createChatRoomMember(newDm, user));
        members.add(ChatRoomMemberConverter.createChatRoomMember(newDm, friend));
        chatRoomMemberRepository.saveAll(members);

        //DM채팅방에 멤버 객체 넣기
        newDm.setMembers(members);

        return ChatRoomConverter.convertToChatRoomDto(chatRoomRepository.save(newDm));
    }

    //현재 접속한 유저의 채팅방 리스트 가져오기
    public List<ResponseDto.ChatRoomListResponseDto> getUserChatRooms() {
        //현재 접속한 유저 정보 가져옴
        User user = securityUtil.getCurrentUser();

        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByUser(user);

        return chatRooms.stream()
                .map(chatRoom -> {
                    String chatRoomTitle = chatRoom.getTitle();

                    //dm이면 상대방 이름 삽입
                    if (chatRoom.getType().equals(ChatRoomType.DM)){
                        //내가 아닌 사용자 뽑아오기
                        ChatRoomMember friendChatRoomMember = chatRoom.getMembers().stream()
                                .filter(member -> !member.getUser().getUserId().equals(user.getUserId()))
                                .findFirst()
                                .orElse(null);

                        //상대방이 null이 아니고 나와 사용자 친구관계 존재 확인
                        if (friendChatRoomMember != null && friendRepository.existsByUserAndFriend(user, friendChatRoomMember.getUser())){
                            Friend friend = friendRepository.findByUserAndFriend(user, friendChatRoomMember.getUser())
                                    .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

                            //친구 닉네임으로 채팅방 제목 설정
                            chatRoomTitle = friend.getFriendNickname();
                        }
                        //나와 친구가 아니라면
                        else if (friendChatRoomMember != null){
                            chatRoomTitle = friendChatRoomMember.getUser().getUserName();
                        }
                    }
                    //dm이 아니라면
                    else {
                        //현재 사용자의 채팅방 제목 가져오기
                        ChatRoomMember userChatRoomMember = chatRoom.getMembers().stream()
                                .filter(member -> member.getUser().getUserId().equals(user.getUserId()))
                                .findFirst()
                                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));
                        chatRoomTitle = userChatRoomMember.getChatRoomTitle();
                    }

                    Optional<Message> latestMessageOpt = messageRepository.findFirstByChatRoomIdOrderBySendAtDesc(chatRoom.getId());
                    // 메시지가 없으면 빈 문자열을 기본값으로 설정
                    Message latestMessage = latestMessageOpt.orElse(Message.builder().senderId("").content(" ").build());
                    return ChatRoomConverter.convertToChatRoomListDto(chatRoom, chatRoomTitle, latestMessage);
                })
                .sorted(Comparator.comparing(
                        ResponseDto.ChatRoomListResponseDto::getLatestMessageAt,
                        Comparator.nullsLast(Comparator.reverseOrder()))//최신순으로 정렬
                )
                .collect(Collectors.toList());
    }

    //채팅방 정보 조회
    public ResponseDto.ChatRoomDetailDto getChatRoomDetails(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID));

        //현재 접속한 유저 정보 가져옴
        User user = securityUtil.getCurrentUser();

        List<ResponseDto.ChatRoomMemberInfoDto> members =  new ArrayList<>();

        for(ChatRoomMember member : chatRoom.getMembers()){
            //초기세팅
            String userName = member.getUser().getUserName();
            String userId = member.getUser().getUserId();
            FriendType type = FriendType.NOT_FRIEND;

            //멤버가 나인지 확인
            if(user.getUserId().equals(member.getUser().getUserId())){
                userName = member.getUser().getUserName();
                userId = member.getUser().getUserId();
                type = FriendType.ME;
            }
            //멤버가 친구인지 확인
            else if (friendRepository.existsByUserAndFriend(user, member.getUser())){
                Friend friend = friendRepository.findByUserAndFriend(user, member.getUser())
                        .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

                userName = friend.getFriendNickname();
                userId = member.getUser().getUserId();
                type = FriendType.FRIEND;
            }

            ResponseDto.ChatRoomMemberInfoDto chatRoomMemberInfoDto = ResponseDto.ChatRoomMemberInfoDto.builder()
                    .userName(userName)
                    .userId(userId)
                    .type(type)
                    .build();

            members.add(chatRoomMemberInfoDto);
        }

        return ChatRoomConverter.convertToChatRoomDetailDto(chatRoom, members);
    }

    //채팅방 초대
    @Transactional
    public void addMemberToChatRoom(Long roomId, RequestDto.FriendIdDto friendIdDto) {
        //친구 아이디 가져오기
        String friendId = friendIdDto.getFriendId();

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

        System.out.println(friendId);

        User user = userRepository.findByUserId(friendId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //새로운 멤버 추가
        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.setChatRoom(chatRoom);
        chatRoomMember.setUser(user);

        // 저장
        chatRoomMemberRepository.save(chatRoomMember);
    }

    //채팅방 다중 멤버 초대
    @Transactional
    public void addMembersToChatRoom(Long roomId, RequestDto.FriendIdsDto friendIdsDto) {

        //친구 아이디들 가져오기
        List<String> memberIds = friendIdsDto.getMemberIds();

        //채팅방 존재 확인
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID));

        //개인 채팅방인지 확인
        if (chatRoom.getType().equals(ChatRoomType.DM)) {
            throw new IllegalArgumentException("개인 채팅방은 친구추가를 할 수 없습니다.");
        }

        //멤버인지 확인하고 유저 객체로 만듦
        List<User> members = memberIds.stream()
            //이미 채팅방 멤버면 필터링
            .filter(memberId -> !chatRoomMemberRepository.existsByChatRoomIdAndUserUserId(roomId, memberId))
                .map(memberId -> userRepository.findByUserId(memberId)
                    .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER)))
                .toList();

        //새로운 멤버 추가
        members.forEach(member -> {
            chatRoomMemberRepository.save(ChatRoomMemberConverter.createChatRoomMember(chatRoom, member));
        });
    }

    //채팅방 이름 수정
    @Transactional
    public void setChatRoomTitle(Long roomId, RequestDto.ChatRoomTitleDto chatRoomTitleDto){
        //채팅방 존재 확인
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_ROOM_ID));

        //현재 접속한 사용자 정보 가져오기
        User user = securityUtil.getCurrentUser();

        //현재 접속한 사용자의 chatRoomMember정보 가져오기
        ChatRoomMember chatRoomMember = chatRoom.getMembers().stream()
                .filter(member -> member.getUser().getUserId().equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //채팅방 이름 수정
        chatRoomMember.setChatRoomTitle(chatRoomTitleDto.getTitle());

        chatRoomMemberRepository.save(chatRoomMember);
    }


    //채팅방 나가기
    @Transactional
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


