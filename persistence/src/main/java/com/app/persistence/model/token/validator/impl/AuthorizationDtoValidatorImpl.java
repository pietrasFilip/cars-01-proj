package com.app.persistence.model.token.validator.impl;

import com.app.persistence.model.token.dto.AuthorizationDto;
import com.app.persistence.model.token.validator.AuthorizationDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.persistence.model.token.validator.generic.TokenValidator.validateNull;

@Component
public class AuthorizationDtoValidatorImpl implements AuthorizationDtoValidator {
    @Override
    public void validate(AuthorizationDto authorizationDto) {
        validateNull(authorizationDto.role(),"AuthenticationDto - username is null or empty");
    }
}
