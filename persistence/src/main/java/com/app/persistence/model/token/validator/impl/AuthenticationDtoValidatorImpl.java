package com.app.persistence.model.token.validator.impl;

import com.app.persistence.model.token.dto.AuthenticationDto;
import com.app.persistence.model.token.validator.AuthenticationDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.persistence.model.token.validator.generic.TokenValidator.validateNull;

@Component
public class AuthenticationDtoValidatorImpl implements AuthenticationDtoValidator {
    @Override
    public void validate(AuthenticationDto authenticationDto) {
        validateNull(authenticationDto.username(),"AuthenticationDto - username is null or empty");
        validateNull(authenticationDto.password(), "Password is null or empty");
    }
}
