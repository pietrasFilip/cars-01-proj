package com.app.service.user;

import com.app.persistence.model.token.dto.AuthenticationDto;
import com.app.persistence.model.user.dto.CreateUserDto;
import com.app.persistence.model.user.dto.GetUserDto;

public interface UserService {
    GetUserDto register(CreateUserDto createUserDto);
    GetUserDto activate(Long userId, Long expirationTime);
    GetUserDto findById(Long id);
    GetUserDto findByUsername(String username);
    GetUserDto findByEmail(String email);
    Long isUserCorrect(AuthenticationDto authenticationDto);
}
