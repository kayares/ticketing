package com.kayares.ticketing.service;

import com.kayares.ticketing.domain.User;
import com.kayares.ticketing.dto.UserResponse;
import com.kayares.ticketing.exception.DuplicateUsernameException;
import com.kayares.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse create(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException(username);
        }
        User user = userRepository.save(new User(username, password));
        return UserResponse.from(user);
    }
}
