package com.app.persistence.model.token.validator.impl;

import com.app.persistence.model.token.dto.TokensDto;
import com.app.persistence.model.token.validator.TokensDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.persistence.model.token.validator.generic.TokenValidator.validateNull;

@Component
public class TokensDtoValidatorImpl implements TokensDtoValidator {
    @Override
    public void validate(TokensDto tokensDto) {
        validateNull(tokensDto.access(), "TokensDto - access is null or empty");
        validateNull(tokensDto.refresh(), "TokensDto - refresh is null or empty");
    }
}
