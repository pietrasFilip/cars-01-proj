package com.app.persistence.model.user.dto;

import com.app.persistence.model.user.Role;
import com.app.persistence.model.user.User;

public record CreateUserDto(
        String username,
        String email,
        String password,
        String passwordConfirmation,
        Role role
) {
    public User toUser() {
        return User
                .builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(0)
                .build();
    }
}
