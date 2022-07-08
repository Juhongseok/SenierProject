package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.Friend;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.compositid.FriendId;
import com.jhs.seniorProject.domain.exception.DuplicateFriendException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.repository.FriendRepository;
import com.jhs.seniorProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    /**
     * 친구 추가
     * 1. 친구 추가 검증로직
     * 2-1. 오류가 없으면 친구추가
     * 2-2. 오류가 있으면 throw new DuplicatedFriendException
     * @param user session 저장된 로그인 유저
     * @param friendId 사용자가 검색한 유저
     * @throws NoSuchUserException
     */
    public void addFriend(User user, String friendId) throws DuplicateFriendException, NoSuchUserException {
        User friend = findFriend(friendId);
        validateMyself(user.getId(), friendId);
        validateDuplicatedFriend(user, friend);
        FriendId friendCompositeId = new FriendId(user.getId(), friend.getId());
        Friend friendRelation = new Friend(friendCompositeId, user, friend);
        friendRepository.save(friendRelation);
    }
    /**
     * 친구목록보기
     * @param user session 저장된 로그인 유저
     * @return 사용자와 친구 관계를 가진 유저 리스트 (list.size() >= 0)
     */
    @Transactional(readOnly = true)
    public List<Friend> getFriends(User user){
        //TODO Entity -> DTO 변경
        return friendRepository.findByUserId(user);
    }

    /**
     * 친구추가 창에서 아이디를 기준으로 검색을 할 때 사용
     * @param friendId
     * @return 아이디를 가진 사용자
     * @throws NoSuchUserException
     */
    private User findFriend(String friendId) throws NoSuchUserException {
        //TODO Entity -> DTO 변경
        return userRepository.findById(friendId).orElseThrow(() -> new NoSuchUserException(friendId + "의 유저가 없습니다."));
    }

    /**
     * 자기 자신 추가 못하도록 검증
     * @param userId
     * @param friendId
     * @throws DuplicateFriendException
     */
    private void validateMyself(String userId, String friendId) throws DuplicateFriendException {
        if (userId.equals(friendId)) {
            throw new DuplicateFriendException("나자신과는 친구관계를 맺을수 없습니다.");
        }
    }
    /**
     * 친구추가 검증
     * 친구관계가 이미 맺어진 상태인지 확인
     * @param user
     * @param friend
     * @throws DuplicateFriendException
     */
    private void validateDuplicatedFriend(User user, User friend) throws DuplicateFriendException {
        if (friendRepository.findByUserIdAndFriendId(user, friend) != null) {
            throw new DuplicateFriendException(friend.getId() + "와 이미 관계가 맺어져 있습니다.");
        }
    }
}
