package com.example.kameleoon.model.entity.dto;

import com.example.kameleoon.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private Instant createdAt;

    public static UserDTO toDto(User entity) {
        return new UserDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getCreatedAt());
    }
}
