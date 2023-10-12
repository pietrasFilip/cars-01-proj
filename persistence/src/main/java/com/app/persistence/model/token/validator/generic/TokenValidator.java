package com.app.persistence.model.token.validator.generic;

import com.app.persistence.model.token.dto.exception.TokenDtoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface TokenValidator <T>{
    Logger logger = LogManager.getRootLogger();
    void validate(T t);

    static <T> T validateNull(T t, String logMessage) {
        if (t == null) {
            logger.error(logMessage);
            throw new TokenDtoException("Is null");
        }
        return t;
    }
}
