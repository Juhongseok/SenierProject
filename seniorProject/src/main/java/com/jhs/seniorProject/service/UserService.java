package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.domain.exception.IncorrectPasswordException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String join(User user) throws DuplicatedUserException {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    public void withdrawal(String id) throws NoSuchUserException {
        userRepository.delete(ifHasUser(id));
    }

    @Transactional(readOnly = true)
    public User login(User user) throws NoSuchUserException, IncorrectPasswordException {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchUserException("일치하는 회원이 없습니다"));
        if (!findUser.getPassword().equals(user.getPassword()))
            throw new IncorrectPasswordException("비밀번호가 일치하지 않습니다.");
        return findUser;
    }

    private User ifHasUser(String id) throws NoSuchUserException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException("일치하는 회원이 없습니다"));
    }

    private void validateDuplicateUser(User user) throws DuplicatedUserException {
        if (userRepository.findById(user.getId()).isPresent())
            throw new DuplicatedUserException("이미 존재하는 회원입니다");
    }
}
