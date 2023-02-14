package com.example.kameleoon.service;

import com.example.kameleoon.exception.UserAlreadyExistException;
import com.example.kameleoon.model.entity.User;
import com.example.kameleoon.model.entity.dto.UserDTO;

public interface UserService {

    UserDTO registerUser(User user) throws UserAlreadyExistException;
}
