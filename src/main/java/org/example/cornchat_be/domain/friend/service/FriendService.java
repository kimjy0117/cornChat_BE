package org.example.cornchat_be.domain.friend.service;

import lombok.AllArgsConstructor;
import org.example.cornchat_be.apiPayload.code.status.ErrorStatus;
import org.example.cornchat_be.apiPayload.exception.CustomException;
import org.example.cornchat_be.domain.friend.converter.FriendConverter;
import org.example.cornchat_be.domain.friend.dto.FriendRequestDto;
import org.example.cornchat_be.domain.friend.dto.FriendResponseDto;
import org.example.cornchat_be.domain.friend.entity.Friend;
import org.example.cornchat_be.domain.friend.repository.FriendRepository;
import org.example.cornchat_be.domain.user.entity.User;
import org.example.cornchat_be.domain.user.repository.UserRepository;
import org.example.cornchat_be.util.SecurityUtil;
import org.example.cornchat_be.util.jwt.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    //아이디로 친구 추가
    @Transactional
    public void addFriendByFriendId(String friendId) {
            //현재 접속한 유저 정보 가져옴
            User user = securityUtil.getCurrentUser();

            //친구추가할 사용자 정보 추출
            User friend = userRepository.findByUserId(friendId)
                    .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

            //현재 접속한 유저와 친구추가할 사용자가 일치하는지 확인
            if(user.getUserId().equals(friend.getUserId())){
                throw new CustomException(ErrorStatus._ADD_FRIEND_MYSELF);
            }

            //이미 친구 관계가 존재하는지 확인
            friendRepository.findByUserAndFriend(user, friend).ifPresent((existing) -> {
                throw new CustomException(ErrorStatus._DUPLICATED_FRIEND);
            });

            //친구 생성
            Friend newFriend = Friend.builder()
                    .user(user)
                    .friend(friend)
                    .friendNickname(friend.getUserName())
                    .build();

            //친구 저장
            friendRepository.save(newFriend);
    }

    //핸드폰 번호로 친구 추가
    @Transactional
    public void addFriendByFriendPhoneNum(String friendPhoneNum){
            //현재 접속한 유저 정보 가져옴
            User user = securityUtil.getCurrentUser();

            //친구추가할 사용자 정보 추출
            User friend = userRepository.findByPhoneNum(friendPhoneNum)
                    .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

            //현재 접속한 유저와 친구추가할 사용자가 일치하는지 확인
            if(user.getUserId().equals(friend.getUserId())){
                throw new CustomException(ErrorStatus._ADD_FRIEND_MYSELF);
            }

            //이미 친구 관계가 존재하는지 확인
            friendRepository.findByUserAndFriend(user, friend).ifPresent((existing) -> {
                throw new CustomException(ErrorStatus._DUPLICATED_FRIEND);
            });

            //친구 생성
            Friend newFriend = Friend.builder()
                    .user(user)
                    .friend(friend)
                    .friendNickname(friend.getUserName())
                    .build();

            //친구 저장
            friendRepository.save(newFriend);
    }

    //친구 이름 수정
    @Transactional
    public void setFriendName(FriendRequestDto.friendNameDto friendNameDto){
        //현재 접속한 유저 정보 가져옴
        User user = securityUtil.getCurrentUser();

        //친구추가할 사용자 정보 추출
        User friend = userRepository.findByUserId(friendNameDto.getFriendId())
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //친구관계 정보 가져오기
        Friend friendRelation = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

        if(friendRelation.getFriendNickname().equals(friendNameDto.getFriendName())){
            throw new IllegalStateException("기존 이름과 동일합니다.");
        }

        //친구 닉네임 수정
        friendRelation.setFriendNickname(friendNameDto.getFriendName());
        friendRepository.save(friendRelation);
    }

    //친구 삭제
    @Transactional
    public void removeFriend(String friendId){
        //현재 인증된 유저 정보 가져오기
        User user = securityUtil.getCurrentUser();

        //친구 정보 가져오기
        User friend = userRepository.findByUserId(friendId)
                .orElseThrow(() -> new CustomException(ErrorStatus._NOT_EXIST_USER));

        //친구관계 정보 가져오기
        Friend friendRelation = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 관계가 존재하지 않습니다."));

        //유저 리스트에서 친구 제거
        user.getFriends().remove(friendRelation);

        //친구관계 삭제
        friendRepository.deleteById(friendRelation.getId());
    }

    //친구 목록 조회
    public List<FriendResponseDto> getFriends (){
        //현재 인증된 유저 정보 가져오기
        User user = securityUtil.getCurrentUser();

        //현재 인증된 유저와 친구인 목록 조회
        List<Friend> friends = friendRepository.findByUser(user);

        return friends.stream()
                .filter(f -> f.getFriend() != null)
                .sorted((f1, f2) -> {
                    boolean f1IsKorean = f1.getFriendNickname().matches("^[가-힣].*");
                    boolean f2IsKorean = f2.getFriendNickname().matches("^[가-힣].*");

                    if (f1IsKorean && !f2IsKorean) {
                        return -1; // f1이 한글이고 f2가 영어인 경우
                    } else if (!f1IsKorean && f2IsKorean) {
                        return 1;  // f2가 한글이고 f1이 영어인 경우
                    } else {
                        return f1.getFriendNickname().compareTo(f2.getFriendNickname());
                    }
                })
                .map(FriendConverter::createFriendResponseDto)  // FriendConverter 사용
                .collect(Collectors.toList());
    }
}
