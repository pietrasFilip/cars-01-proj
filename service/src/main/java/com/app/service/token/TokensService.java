package com.app.service.token;

import com.app.persistence.model.token.dto.AuthorizationDto;
import com.app.persistence.model.token.dto.RefreshTokenDto;
import com.app.persistence.model.token.dto.TokensDto;

public interface TokensService {
    TokensDto generateTokens(Long userId);
    AuthorizationDto parseTokens(String token);
    TokensDto refreshTokens(RefreshTokenDto refreshTokenDto);
}
