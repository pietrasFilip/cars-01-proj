package com.app.persistence.model.token.validator.impl;

import com.app.persistence.model.token.dto.RefreshTokenDto;
import com.app.persistence.model.token.validator.RefreshTokenDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.persistence.model.token.validator.generic.TokenValidator.validateNull;

@Component
public class RefreshTokenDtoValidatorImpl implements RefreshTokenDtoValidator {
    @Override
    public void validate(RefreshTokenDto refreshTokenDto) {
        validateNull(refreshTokenDto.token(), "RefreshTokenDto - token is null or empty");
    }
}
