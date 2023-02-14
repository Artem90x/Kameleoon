package com.example.kameleoon.service.impl;

import com.example.kameleoon.exception.UserAlreadyExistException;
import com.example.kameleoon.model.entity.User;
import com.example.kameleoon.model.entity.dto.UserDTO;
import com.example.kameleoon.repository.UserRepository;
import com.example.kameleoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDTO registerUser(User user) throws UserAlreadyExistException {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User already exists");
        }
        return UserDTO.toDto(userRepository.save(user));
    }
}
