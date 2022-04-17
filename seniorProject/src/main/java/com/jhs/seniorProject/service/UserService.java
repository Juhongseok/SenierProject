package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
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

    public void withdrawal(String id) {
        userRepository.delete(userRepository.findById(id).get());
    }

    @Transactional(readOnly = true)
    public User login(User user) {
        log.info("user.id: {}, user.password: {}", user.getId(), user.getPassword());
        return userRepository.findByIdAndPassword(user.getId(), user.getPassword()).orElse(null);
    }

    private void validateDuplicateUser(User user) throws DuplicatedUserException {
        if(userRepository.findById(user.getId()).isPresent())
            throw new DuplicatedUserException("이미 존재하는 회원입니다");
    }

    public User findUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
